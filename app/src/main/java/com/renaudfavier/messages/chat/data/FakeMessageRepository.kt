package com.renaudfavier.messages.chat.data

import com.renaudfavier.messages.chat.domain.Message
import com.renaudfavier.messages.chat.domain.MessageId
import com.renaudfavier.messages.chat.domain.MessageRepository
import com.renaudfavier.messages.core.domain.ContactId
import com.renaudfavier.messages.core.domain.toContactId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeMessageRepository @Inject constructor(): MessageRepository {

    private val messages = MutableStateFlow(sampleMessages)

    override fun getAllConversations(): Flow<List<ContactId>> {
        val currentUserId = 0.toContactId()
        return messages.map { messageList ->
            messageList
                .flatMap { listOf(it.author, it.recipient) }
                .filter { it != currentUserId }
                .distinct()
                .sortedBy { contactId ->
                    messageList
                        .filter { it.author == contactId || it.recipient == contactId }
                        .maxOfOrNull { it.date }
                }
                .reversed()
        }
    }

    override fun getConversationFlow(contactId: ContactId): Flow<List<Message>> {
        return messages.map { messageList ->
            messageList
                .filter {
                    it.author == contactId || it.recipient == contactId
                }
                .sortedBy { it.date }
        }
    }

    override suspend fun sendMessage(message: Message): Result<Unit> {
        messages.value = messages.value + message
        return Result.success(Unit)
    }

    override suspend fun messageWasRead(id: MessageId): Result<Unit> {
        val messageIndex = messages.value.indexOfFirst { it.id == id }
        if (messageIndex != -1) {
            messages.value = messages.value.toMutableList().apply {
                set(messageIndex, get(messageIndex).copy(isUnread = false))
            }
            return Result.success(Unit)
        }
        return Result.failure(Error("Message not found"))
    }
}