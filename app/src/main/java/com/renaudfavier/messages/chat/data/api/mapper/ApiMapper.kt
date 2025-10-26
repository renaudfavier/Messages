package com.renaudfavier.messages.chat.data.api.mapper

import com.renaudfavier.messages.chat.data.api.model.ChatDto
import com.renaudfavier.messages.chat.data.api.model.MessageDto
import com.renaudfavier.messages.chat.domain.Message
import com.renaudfavier.messages.core.domain.Contact
import com.renaudfavier.messages.core.domain.ContactId
import com.renaudfavier.messages.core.domain.toContactId
import java.time.Instant
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ApiMapper @Inject constructor() {

    fun mapChatToContact(chat: ChatDto): Contact {
        return Contact(
            id = chat.id.toContactId(),
            name = chat.name,
            avatar = com.renaudfavier.messages.R.drawable.ic_launcher_foreground // Default avatar
        )
    }

    fun mapMessage(messageDto: MessageDto): Message {
        val currentUserId = 0.toContactId() // User is always ID 0
        val isFromUser = messageDto.author == "user"

        return Message(
            id = messageDto.id,
            author = if (isFromUser) currentUserId else messageDto.chatId.toContactId(),
            recipient = if (isFromUser) messageDto.chatId.toContactId() else currentUserId,
            date = parseInstant(messageDto.sentAt),
            content = messageDto.text,
            isUnread = !isFromUser // Messages from user are already read
        )
    }

    private fun parseInstant(sentAt: String): Instant {
        return try {
            Instant.parse(sentAt)
        } catch (e: Exception) {
            // Fallback if parsing fails
            Instant.now()
        }
    }
}
