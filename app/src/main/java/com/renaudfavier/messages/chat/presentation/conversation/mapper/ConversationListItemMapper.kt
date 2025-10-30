package com.renaudfavier.messages.chat.presentation.conversation.mapper

import com.renaudfavier.messages.chat.domain.Message
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel
import com.renaudfavier.messages.core.domain.toContactId
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ConversationListItemMapper @Inject constructor() {
    fun map(message: Message): ConversationListItemUiModel.MessageUiModel {
        return when {
            message.content.isAnEmoji() -> mapEmoji(message)
            else -> mapMessage(message)
        }
    }

    fun map(date: LocalDate) = ConversationListItemUiModel.TimeUiModel(
        id = "date_${date}",
        text = formatDate(date)
    )

    private fun mapMessage(message: Message) =
        ConversationListItemUiModel.MessageUiModel.TextMessage(
            id = "${message.id}",
            isMine = message.author == "user".toContactId(),
            text = message.content,
        )
    private fun mapEmoji(message: Message) =
        ConversationListItemUiModel.MessageUiModel.EmojiMessage(
            id = "${message.id}",
            isMine = message.author == "user".toContactId(),
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

    private fun formatDate(date: LocalDate): String {
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        return when (date) {
            today -> "Today"
            yesterday -> "Yesterday"
            else -> {
                // For older dates, show the formatted date
                val daysAgo = java.time.temporal.ChronoUnit.DAYS.between(date, today)
                if (daysAgo < 7) {
                    // Within the past week, show day of week
                    date.format(DateTimeFormatter.ofPattern("EEEE"))
                } else {
                    // Older than a week, show full date
                    date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
                }
            }
        }
    }
}
