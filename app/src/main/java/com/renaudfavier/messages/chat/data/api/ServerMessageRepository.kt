package com.renaudfavier.messages.chat.data.api

import com.renaudfavier.messages.chat.data.api.mapper.ApiMapper
import com.renaudfavier.messages.chat.data.api.model.SendMessageRequest
import com.renaudfavier.messages.chat.domain.Message
import com.renaudfavier.messages.chat.domain.MessageId
import com.renaudfavier.messages.chat.domain.MessageRepository
import com.renaudfavier.messages.core.domain.ContactId
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerMessageRepository @Inject constructor(
    private val apiService: MessagesApiService,
    private val eventStream: MessageEventStream,
    private val apiMapper: ApiMapper
) : MessageRepository {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("SSE: Uncaught exception in repository scope: ${throwable.message}")
        throwable.printStackTrace()
    }
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)
    private val messagesCache = MutableStateFlow<Map<MessageId, Message>>(emptyMap())
    private val seenMessageIds = mutableSetOf<String>()

    init {
        observeServerEvents()

        scope.launch {
            try {
                loadChatsFromServer()
                seenMessageIds.addAll(messagesCache.value.keys)
            } catch (e: Exception) {
                println("Failed to load chats on init: ${e.message}")
            }
        }
    }

    private fun observeServerEvents() {
        scope.launch {
            while (scope.isActive) {
                try {
                    eventStream.observeMessages(seenMessageIds)
                        .collect { message ->
                            messagesCache.update { cache ->
                                cache + (message.id to message)
                            }
                        }
                } catch (e: Exception) {
                    println("Silently crash: ${e.message}, reconnecting")
                }
            }
        }
    }

    override fun getAllConversations(): Flow<List<ContactId>> {
        return messagesCache.map { cache ->
            val currentUserId = "user"
            cache.values
                .flatMap { listOf(it.author, it.recipient) }
                .filter { it.id != currentUserId }
                .distinct()
                .sortedByDescending { contactId ->
                    cache.values
                        .filter { it.author == contactId || it.recipient == contactId }
                        .maxOfOrNull { it.date }
                }
        }
    }

    override fun getConversationFlow(contactId: ContactId): Flow<List<Message>> {
        return messagesCache.map { cache ->
            cache.values
                .filter { it.author == contactId || it.recipient == contactId }
                .sortedBy { it.date }
        }
    }

    override suspend fun sendMessage(id: String, text: String): Result<Unit> = scope.async {
        try {
            val idempotencyKey = UUID.randomUUID().toString()
            val request = SendMessageRequest(text = text)

            val responseDto = withExponentialBackoff {
                apiService.sendMessage(
                    chatId = id,
                    idempotencyKey = idempotencyKey,
                    request = request
                )
            }

            val mappedMessage = apiMapper.mapMessage(responseDto)
            messagesCache.update { cache ->
                cache + (mappedMessage.id to mappedMessage)
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }.await()

    override suspend fun chatWasRead(id: ContactId): Result<Unit> {
        messagesCache.update { cache ->
            cache.mapValues { (_, message) ->
                if (message.author == id && message.isUnread) {
                    message.copy(isUnread = false)
                } else {
                    message
                }
            }
        }
        return Result.success(Unit)
    }

    private suspend fun loadChatsFromServer() {
        withExponentialBackoff { apiService.getChats() }.forEach { chat ->
            try {
                loadMessagesForChat(chat.id)
            } catch (e: Exception) {
                // Continue loading other chats even if one fails
                println("Failed to load messages for chat ${chat.id}: ${e.message}")
            }
        }
    }

    private suspend fun loadMessagesForChat(chatId: String) {
        val messageDtos = withExponentialBackoff { apiService.getMessages(chatId) }

        messagesCache.update { cache ->
            val messages = messageDtos
                .filter { it.id !in cache } // skip already cached ones
                .map { apiMapper.mapMessage(it) }
                .associateBy { it.id }

            cache + messages
        }
    }

    suspend fun <T> withExponentialBackoff(block: suspend () -> T) = retryWithExponentialBackoff(maxRetries = 5) {
        try {
            val res = block()
            Result.success(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }.getOrThrow()

    private suspend fun <T> retryWithExponentialBackoff(
        maxRetries: Int,
        block: suspend () -> Result<T>
    ): Result<T> {
        var currentDelay = 500L
        repeat(maxRetries) { attempt ->
            val result = block()
            if (result.isSuccess) {
                return result
            }

            if (attempt < maxRetries - 1) {
                delay(currentDelay)
                currentDelay *= 2 // Exponential backoff
            }
        }

        return block() // Final attempt
    }
}
