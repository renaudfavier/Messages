package com.renaudfavier.messages.chat.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val text: String
)
