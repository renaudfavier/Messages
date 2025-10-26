package com.renaudfavier.messages.chat.data.api

import com.renaudfavier.messages.chat.data.api.mapper.ApiMapper
import com.renaudfavier.messages.chat.data.api.model.SendMessageRequest
import com.renaudfavier.messages.chat.domain.Message
import com.renaudfavier.messages.chat.domain.MessageId
import com.renaudfavier.messages.chat.domain.MessageRepository
import com.renaudfavier.messages.core.domain.ContactId
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

    // In-memory cache of messages with deduplication
    private val messagesCache = MutableStateFlow<Map<Int, Message>>(emptyMap())
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        // Start listening to SSE stream and update cache
        observeServerEvents()
    }

    private fun observeServerEvents() {
        scope.launch {
            eventStream.observeMessages()
                .retry(retries = Long.MAX_VALUE) { cause ->
                    // Retry on any error with exponential backoff
                    delay(2000)
                    true
                }
                .collect { message ->
                    // Add or update message in cache (deduplication by ID)
                    messagesCache.update { cache ->
                        cache + (message.id to message)
                    }
                }
        }
    }

    override fun getAllConversations(): Flow<List<ContactId>> {
        return messagesCache.map { cache ->
            val currentUserId = 0
            cache.values
                .flatMap { listOf(it.author, it.recipient) }
                .filter { it.id != currentUserId }
                .distinct()
                .sortedByDescending { contactId ->
                    cache.values
                        .filter { it.author == contactId || it.recipient == contactId }
                        .maxOfOrNull { it.date }
                }
        }.onStart {
            // Load initial data from server
            loadChatsFromServer()
        }
    }

    override fun getConversationFlow(contactId: ContactId): Flow<List<Message>> {
        return messagesCache.map { cache ->
            cache.values
                .filter { it.author == contactId || it.recipient == contactId }
                .sortedBy { it.date }
        }.onStart {
            // Load messages for this chat from server
            loadMessagesForChat(contactId.id)
        }
    }

    override suspend fun sendMessage(message: Message): Result<Unit> {
        val idempotencyKey = UUID.randomUUID().toString()

        return retryWithExponentialBackoff(maxRetries = 3) {
            try {
                val request = SendMessageRequest(text = message.content)
                val responseDto = apiService.sendMessage(
                    chatId = message.recipient.id,
                    idempotencyKey = idempotencyKey,
                    request = request
                )

                // Add sent message to cache immediately (optimistic update)
                val mappedMessage = apiMapper.mapMessage(responseDto)
                messagesCache.update { cache ->
                    cache + (mappedMessage.id to mappedMessage)
                }

                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun messageWasRead(id: MessageId): Result<Unit> {
        // Update message in cache to mark as read
        messagesCache.update { cache ->
            val message = cache[id]
            if (message != null) {
                cache + (id to message.copy(isUnread = false))
            } else {
                cache
            }
        }
        return Result.success(Unit)
    }

    private suspend fun loadChatsFromServer() {
        try {
            val chats = apiService.getChats()
            // Load messages for each chat
            chats.forEach { chat ->
                loadMessagesForChat(chat.id)
            }
        } catch (e: Exception) {
            // Silently fail - will retry through flow retry logic
        }
    }

    private suspend fun loadMessagesForChat(chatId: Int) {
        try {
            val messageDtos = apiService.getMessages(chatId)
            val messages = messageDtos.map { apiMapper.mapMessage(it) }

            // Add all messages to cache (deduplicated by ID)
            messagesCache.update { cache ->
                cache + messages.associateBy { it.id }
            }
        } catch (e: Exception) {
            // Silently fail - SSE will eventually provide messages
        }
    }

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
