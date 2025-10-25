package com.renaudfavier.messages.core.data

import com.renaudfavier.messages.R
import com.renaudfavier.messages.core.domain.Contact
import com.renaudfavier.messages.core.domain.toContactId

val sampleContacts = mapOf(
    1.toContactId() to Contact(1.toContactId(), "John", R.drawable.john),
    2.toContactId() to Contact(2.toContactId(), "Peter", R.drawable.peter),
    3.toContactId() to Contact(3.toContactId(), "Sarah", R.drawable.sarah),
    4.toContactId() to Contact(4.toContactId(), "Léa", R.drawable.lea),
)
