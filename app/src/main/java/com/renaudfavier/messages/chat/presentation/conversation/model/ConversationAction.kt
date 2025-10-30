package com.renaudfavier.messages.chat.presentation.conversation.model

sealed interface ConversationAction {
    data class SendMessage(val message: String): ConversationAction
    data class MessageChanged(val message: String): ConversationAction
    data object ConversationViewed: ConversationAction
    data object Retry: ConversationAction
}
