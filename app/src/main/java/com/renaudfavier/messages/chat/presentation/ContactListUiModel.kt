package com.renaudfavier.messages.chat.presentation

sealed interface ContactListUiModel {
    data object Loading: ContactListUiModel
    data class Content(
        val contacts: List<ContactListItemUiModel>
    ): ContactListUiModel
}

data class ContactListItemUiModel(
    val contactId: Int,
    val name: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val avatarRes: Int,
    val isUnread: Boolean,
)
