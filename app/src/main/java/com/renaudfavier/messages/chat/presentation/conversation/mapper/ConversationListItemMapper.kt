package com.renaudfavier.messages.chat.presentation.conversation.mapper

import com.renaudfavier.messages.chat.domain.Message
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel
import com.renaudfavier.messages.core.domain.toContactId
import javax.inject.Inject

class ConversationListItemMapper @Inject constructor() {
    fun map(message: Message): ConversationListItemUiModel.MessageUiModel {
        return when {
            message.content.isAnEmoji() -> mapEmoji(message)
            else -> mapMessage(message)
        }
    }

    private fun mapMessage(message: Message) =
        ConversationListItemUiModel.MessageUiModel.TextMessage(
            id = "${message.id}",
            isMine = message.author == 0.toContactId(),
            text = message.content,
        )
    private fun mapEmoji(message: Message) =
        ConversationListItemUiModel.MessageUiModel.EmojiMessage(
            id = "${message.id}",
            isMine = message.author == 0.toContactId(),
            emoji = message.content,
        )

    private fun String.isAnEmoji(): Boolean {
        val cleaned = this.trim()
        if (cleaned.isEmpty()) return false

        // A simple heuristic: check if the string is short (1-4 characters accounting for multi-codepoint emojis)
        // and doesn't contain alphanumeric characters
        if (cleaned.length > 8) return false

        // Check if it contains any letter or digit
        if (cleaned.any { it.isLetterOrDigit() }) return false

        // Check if it has emoji characteristics (symbols, pictographs, etc.)
        return cleaned.codePoints().allMatch { codePoint ->
            val type = Character.getType(codePoint)
            type == Character.OTHER_SYMBOL.toInt() ||  // Most emojis
            type == Character.MODIFIER_SYMBOL.toInt() ||
            type == Character.SURROGATE.toInt() ||
            codePoint == 0x200D ||  // Zero-width joiner
            codePoint == 0xFE0F ||  // Variation selector
            codePoint in 0x1F000..0x1FFFF ||  // Emoji blocks
            codePoint in 0x2600..0x27BF     // Miscellaneous symbols
        }
    }
}
