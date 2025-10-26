package com.renaudfavier.messages.chat.domain

import com.renaudfavier.messages.core.domain.ContactId
import java.time.Instant

typealias MessageId = String

data class Message(
    val id: MessageId,
    val author: ContactId,
    val recipient: ContactId,
    val date: Instant,
    val content: String,
    val isUnread: Boolean,
)
