package com.renaudfavier.messages.chat.data.api.mapper

import com.renaudfavier.messages.R
import com.renaudfavier.messages.chat.data.api.model.ChatDto
import com.renaudfavier.messages.chat.data.api.model.MessageDto
import com.renaudfavier.messages.chat.domain.Message
import com.renaudfavier.messages.core.domain.Contact
import com.renaudfavier.messages.core.domain.toContactId
import java.time.Instant
import javax.inject.Inject

class ApiMapper @Inject constructor() {

    fun mapChatToContact(chat: ChatDto): Contact {
        return Contact(
            id = chat.id.toContactId(),
            name = chat.name,
            avatar = when(chat.id) {
                "34f9bb23-c25a-4e77-9948-51434cc156b2" -> R.drawable.peter
                "4ccb26d2-22f9-4a58-ba91-ba00bd008bb9" -> R.drawable.sarah
                "74d072a3-2d41-43df-92a0-235659f941ff" -> R.drawable.john
                "ffb2b56d-b39d-4045-a4bc-dd03746a7d7f" -> R.drawable.lea
                else -> R.drawable.ic_launcher_foreground
            } // Default avatar
        )
    }

    fun mapMessage(messageDto: MessageDto): Message {
        val currentUserId = "user".toContactId()
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
