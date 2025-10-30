package com.renaudfavier.messages.core.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class ContactId(val id: String) : Parcelable

fun Int.toContactId() = ContactId("$this")
fun String.toContactId() = ContactId(this)

data class Contact(
    val id: ContactId,
    val name: String,
    val avatar: Int,
)

