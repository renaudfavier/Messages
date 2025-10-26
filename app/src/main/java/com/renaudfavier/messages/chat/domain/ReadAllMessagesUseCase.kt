package com.renaudfavier.messages.chat.domain

import com.renaudfavier.messages.core.domain.ContactId
import javax.inject.Inject

class ReadAllMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {

    suspend fun execute(contactId: ContactId) {
        messageRepository.chatWasRead(contactId)
    }
}
