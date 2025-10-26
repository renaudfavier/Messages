package com.renaudfavier.messages.chat.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: Int,
    val name: String
)
