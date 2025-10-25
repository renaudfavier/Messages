package com.renaudfavier.messages.chat.domain

import com.renaudfavier.messages.core.domain.ContactId
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ReadAllMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    suspend fun execute(contactId: ContactId) {
        messageRepository.getConversationFlow(contactId).first()
            .filter { it.isUnread }
            .forEach { message ->
                messageRepository.messageWasRead(message.id)
            }
    }
}
