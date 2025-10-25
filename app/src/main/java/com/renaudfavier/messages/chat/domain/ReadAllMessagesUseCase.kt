package com.renaudfavier.messages.chat.domain

import com.renaudfavier.messages.core.domain.ContactId
import javax.inject.Inject

class ReadAllMessagesUseCase @Inject constructor(
    messageRepository: MessageRepository
) {

    public fun execute(contactId: ContactId) {

    }
}