package com.renaudfavier.messages.chat.presentation.conversation.model

sealed interface ConversationListItemUiModel {
    data class TimeUiModel(val text: String): ConversationListItemUiModel
    sealed class MessageUiModel(
        open val isMine: Boolean,
    ): ConversationListItemUiModel {
        data class EmojiMessage(
            override val isMine: Boolean,
            val emoji: Char,
        ): MessageUiModel(isMine)

        data class TextMessage(
            override val isMine: Boolean,
            val text: String,
        ): MessageUiModel(isMine)
    }
}
