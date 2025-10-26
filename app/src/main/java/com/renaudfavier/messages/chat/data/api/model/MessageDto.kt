package com.renaudfavier.messages.chat.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: Int,
    @SerialName("chat_id")
    val chatId: Int,
    val author: String, // "user" or "bot"
    val text: String,
    @SerialName("sent_at")
    val sentAt: String,
    @SerialName("idempotency_key")
    val idempotencyKey: String
)
