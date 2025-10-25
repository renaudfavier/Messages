package com.renaudfavier.messages.core.domain

interface ContactRepository {

    suspend fun getMyId(): Result<ContactId>
    suspend fun getContact(id: ContactId): Result<Contact>

}
