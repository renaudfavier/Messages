package com.renaudfavier.messages.chat.presentation.contacts.model

sealed interface ContactListAction {
    data object Retry: ContactListAction
}