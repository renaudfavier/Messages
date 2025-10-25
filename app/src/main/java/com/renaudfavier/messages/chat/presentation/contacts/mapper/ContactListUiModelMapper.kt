package com.renaudfavier.messages.chat.presentation.contacts.mapper

import com.renaudfavier.messages.R
import com.renaudfavier.messages.chat.domain.ConversationSummary
import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListItemUiModel
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import java.time.Instant
import java.time.Duration

class ContactListUiModelMapper @Inject constructor() {

    fun map(summaries: List<ConversationSummary>): List<ContactListItemUiModel> =
        summaries.map{ map(it) }

    private fun map(summary: ConversationSummary): ContactListItemUiModel {
        return ContactListItemUiModel(
            contactId = summary.contactId,
            name = summary.contactName,
            lastMessage = summary.lastMessage.content,
            lastMessageTime = formatDate(summary.lastMessage.date),
            avatarRes = R.drawable.ic_launcher_foreground,
            isUnread = summary.lastMessage.isUnread
        )
    }

    private fun formatDate(date: Instant): String {
        val now = Instant.now()
        val duration = Duration.between(date, now)

        val minutes = duration.toMinutes()
        val hours = duration.toHours()
        val days = duration.toDays()

        return when {
            minutes < 1 -> "now"
            minutes < 60 -> "${minutes}min"
            hours < 24 -> "${hours}h"
            days < 7 -> "${days}d"
            else -> {
                // For older messages, show weeks or date
                val weeks = days / 7
                if (weeks < 4) {
                    "${weeks}w"
                } else {
                    // For very old messages, show the date
                    val localDate = date.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                    localDate.format(DateTimeFormatter.ofPattern("MMM d"))
                }
            }
        }
    }
}
