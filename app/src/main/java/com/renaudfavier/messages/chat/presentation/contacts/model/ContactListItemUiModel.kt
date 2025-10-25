package com.renaudfavier.messages.chat.presentation.contacts.model

import com.renaudfavier.messages.core.domain.ContactId

data class ContactListItemUiModel(
    val contactId: ContactId,
    val name: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val avatarRes: Int,
    val isUnread: Boolean,
)