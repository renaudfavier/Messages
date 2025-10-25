package com.renaudfavier.messages.chat.presentation

import kotlinx.collections.immutable.PersistentList

sealed interface ContactListUiModel {
    data object Loading: ContactListUiModel
    data class Content(
        val contacts: PersistentList<ContactListItemUiModel>
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
