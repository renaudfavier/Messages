package com.renaudfavier.messages.chat.presentation.contacts.component

import com.renaudfavier.messages.R
import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListItemUiModel
import com.renaudfavier.messages.core.domain.toContactId
import kotlinx.collections.immutable.persistentListOf

val sampleContactsListItemUiModel = persistentListOf(
    ContactListItemUiModel(
        contactId = 1.toContactId(),
        name = "Sarah Johnson",
        lastMessage = "See you tomorrow!",
        lastMessageTime = "10:30 AM",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = true
    ),
    ContactListItemUiModel(
        contactId = 2.toContactId(),
        name = "Mike Chen",
        lastMessage = "Thanks for the update 👍",
        lastMessageTime = "9:15 AM",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    ),
    ContactListItemUiModel(
        contactId = 3.toContactId(),
        name = "Emily Rodriguez",
        lastMessage = "Can we schedule a meeting?",
        lastMessageTime = "Yesterday",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = true
    ),
    ContactListItemUiModel(
        contactId = 4.toContactId(),
        name = "James Wilson",
        lastMessage = "Perfect, that works for me",
        lastMessageTime = "Yesterday",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    ),
    ContactListItemUiModel(
        contactId = 5.toContactId(),
        name = "Alex Kim",
        lastMessage = "Did you see the latest design mockups?",
        lastMessageTime = "Tuesday",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    ),
    ContactListItemUiModel(
        contactId = 6.toContactId(),
        name = "Maria Garcia",
        lastMessage = "Great job on the presentation!",
        lastMessageTime = "Monday",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    ),
    ContactListItemUiModel(
        contactId = 7.toContactId(),
        name = "David Lee",
        lastMessage = "Let's catch up soon",
        lastMessageTime = "Last week",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    ),
    ContactListItemUiModel(
        contactId = 8.toContactId(),
        name = "Sophie Martin",
        lastMessage = "Happy birthday! 🎉",
        lastMessageTime = "Last week",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    )
)