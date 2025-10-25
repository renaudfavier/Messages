package com.renaudfavier.messages.chat.presentation.conversation.model

import kotlinx.collections.immutable.PersistentList

sealed interface ConversationUiModel {
    data object Loading: ConversationUiModel
    data class Error(val message: String): ConversationUiModel
    data class Content(
        val contactName: String,
        val contactAvatar: Int,
        val message: String,
        val messages: PersistentList<ConversationListItemUiModel>
    ): ConversationUiModel
}
