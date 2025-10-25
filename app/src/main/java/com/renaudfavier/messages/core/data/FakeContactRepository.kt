package com.renaudfavier.messages.core.data

import com.renaudfavier.messages.core.domain.Contact
import com.renaudfavier.messages.core.domain.ContactId
import com.renaudfavier.messages.core.domain.ContactRepository
import javax.inject.Inject

class FakeContactRepository @Inject constructor(): ContactRepository {

    override suspend fun getAllContacts(): Result<Map<ContactId, Contact>> {
        return Result.success(sampleContacts)
    }

    override suspend fun getContact(id: ContactId) = sampleContacts[id]
        ?.let { Result.success(it) }
        ?: Result.failure(Error("contact not found"))
}
