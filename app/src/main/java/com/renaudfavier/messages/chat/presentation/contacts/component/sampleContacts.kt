package com.renaudfavier.messages.chat.presentation.contacts.component

import com.renaudfavier.messages.R
import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListItemUiModel
import kotlinx.collections.immutable.persistentListOf

val sampleContacts = persistentListOf(
    ContactListItemUiModel(
        contactId = 1,
        name = "Sarah Johnson",
        lastMessage = "See you tomorrow!",
        lastMessageTime = "10:30 AM",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = true
    ),
    ContactListItemUiModel(
        contactId = 2,
        name = "Mike Chen",
        lastMessage = "Thanks for the update 👍",
        lastMessageTime = "9:15 AM",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    ),
    ContactListItemUiModel(
        contactId = 3,
        name = "Emily Rodriguez",
        lastMessage = "Can we schedule a meeting?",
        lastMessageTime = "Yesterday",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = true
    ),
    ContactListItemUiModel(
        contactId = 4,
        name = "James Wilson",
        lastMessage = "Perfect, that works for me",
        lastMessageTime = "Yesterday",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    ),
    ContactListItemUiModel(
        contactId = 5,
        name = "Alex Kim",
        lastMessage = "Did you see the latest design mockups?",
        lastMessageTime = "Tuesday",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    ),
    ContactListItemUiModel(
        contactId = 6,
        name = "Maria Garcia",
        lastMessage = "Great job on the presentation!",
        lastMessageTime = "Monday",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    ),
    ContactListItemUiModel(
        contactId = 7,
        name = "David Lee",
        lastMessage = "Let's catch up soon",
        lastMessageTime = "Last week",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    ),
    ContactListItemUiModel(
        contactId = 8,
        name = "Sophie Martin",
        lastMessage = "Happy birthday! 🎉",
        lastMessageTime = "Last week",
        avatarRes = R.drawable.ic_launcher_foreground,
        isUnread = false
    )
)