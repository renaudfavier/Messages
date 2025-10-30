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
import kotlin.random.Random
import java.time.Instant

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

    override suspend fun sendMessage(id: String, text: String): Result<Unit> {
        val message = Message(
            id = Random.nextInt().toString(),
            author = "user".toContactId(),
            recipient = id.toContactId(),
            date = Instant.now(),
            content = text,
            isUnread = false
        )

        messages.value = messages.value + message
        return Result.success(Unit)
    }

    override suspend fun chatWasRead(id: ContactId): Result<Unit> {
        messages.value = messages.value.map { message ->
            if (message.author == id && message.isUnread) {
                message.copy(isUnread = false)
            } else {
                message
            }
        }
        return Result.success(Unit)
    }
}