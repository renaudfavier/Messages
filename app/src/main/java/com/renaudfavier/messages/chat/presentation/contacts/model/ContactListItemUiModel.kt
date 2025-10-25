package com.renaudfavier.messages.chat.presentation.contacts.model

data class ContactListItemUiModel(
    val contactId: Int,
    val name: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val avatarRes: Int,
    val isUnread: Boolean,
)