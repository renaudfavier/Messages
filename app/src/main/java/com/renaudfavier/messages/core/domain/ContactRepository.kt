package com.renaudfavier.messages.core.domain

interface ContactRepository {

    suspend fun getAllContacts(): Result<Map<ContactId, Contact>>
    suspend fun getContact(id: ContactId): Result<Contact>

}
