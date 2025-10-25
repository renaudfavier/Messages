package com.renaudfavier.messages.chat.presentation.contacts.model

import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListItemUiModel
import kotlinx.collections.immutable.PersistentList

sealed interface ContactListUiModel {
    data object Loading: ContactListUiModel
    data class Error(val message: String): ContactListUiModel
    data class Content(
        val contacts: PersistentList<ContactListItemUiModel>
    ): ContactListUiModel
}