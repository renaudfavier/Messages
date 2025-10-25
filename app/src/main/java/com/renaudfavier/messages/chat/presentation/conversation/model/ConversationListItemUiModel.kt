package com.renaudfavier.messages.chat.presentation.conversation.model

sealed interface ConversationListItemUiModel {
    val id: String

    data class TimeUiModel(
        override val id: String,
        val text: String
    ): ConversationListItemUiModel

    sealed interface MessageUiModel: ConversationListItemUiModel {
        val isMine: Boolean
        data class EmojiMessage(
            override val id: String,
            override val isMine: Boolean,
            val emoji: String,
        ): MessageUiModel

        data class TextMessage(
            override val id: String,
            override val isMine: Boolean,
            val text: String,
        ): MessageUiModel
    }
}
