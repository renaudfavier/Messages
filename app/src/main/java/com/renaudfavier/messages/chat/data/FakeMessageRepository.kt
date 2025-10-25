package com.renaudfavier.messages.chat.data

import com.renaudfavier.messages.chat.domain.Message
import com.renaudfavier.messages.chat.domain.MessageId
import com.renaudfavier.messages.chat.domain.MessageRepository
import com.renaudfavier.messages.core.domain.ContactId
import com.renaudfavier.messages.core.domain.toContactId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeMessageRepository @Inject constructor(): MessageRepository {

    private val messages = sampleMessages.toMutableList()

    override fun getAllConversations(): Flow<List<ContactId>> {
        val currentUserId = 0.toContactId()
        val contactIds = messages
            .flatMap { listOf(it.author, it.recipient) }
            .filter { it != currentUserId }
            .distinct()
            .sortedBy { contactId ->
                messages
                    .filter { it.author == contactId || it.recipient == contactId }
                    .maxOfOrNull { it.date }
            }
            .reversed()

        return flowOf(contactIds)
    }

    override fun getConversationFlow(contactId: ContactId): Flow<List<Message>> {
        val conversationMessages = messages
            .filter {
                it.author == contactId|| it.recipient == contactId
            }
            .sortedBy { it.date }

        return flowOf(conversationMessages)
    }

    override suspend fun sendMessage(message: Message): Result<Unit> {
        messages.add(message)
        return Result.success(Unit)
    }

    override suspend fun messageWasRead(id: MessageId): Result<Unit> {
        val messageIndex = messages.indexOfFirst { it.id == id }
        if (messageIndex != -1) {
            messages[messageIndex] = messages[messageIndex].copy(isUnread = false)
            return Result.success(Unit)
        }
        return Result.failure(Error("Message not found"))
    }
}