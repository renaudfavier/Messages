package com.renaudfavier.messages.chat.presentation.contacts.mapper

import com.renaudfavier.messages.R
import com.renaudfavier.messages.chat.domain.ConversationSummary
import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListItemUiModel
import javax.inject.Inject

class ContactListUiModelMapper @Inject constructor() {

    fun map(summaries: List<ConversationSummary>): List<ContactListItemUiModel> =
        summaries.map{ map(it) }

    private fun map(summary: ConversationSummary): ContactListItemUiModel {
        return ContactListItemUiModel(
            contactId = summary.contactId,
            name = summary.contactName,
            lastMessage = summary.lastMessage.content,
            lastMessageTime = summary.lastMessage.date.toString(),
            avatarRes = R.drawable.ic_launcher_foreground,
            isUnread = summary.lastMessage.isUnread
        )
    }
}
