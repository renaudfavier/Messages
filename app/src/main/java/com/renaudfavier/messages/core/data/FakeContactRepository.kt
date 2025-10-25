package com.renaudfavier.messages.core.data

import com.renaudfavier.messages.core.domain.ContactId
import com.renaudfavier.messages.core.domain.ContactRepository
import com.renaudfavier.messages.core.domain.toContactId
import javax.inject.Inject

class FakeContactRepository @Inject constructor(): ContactRepository {

    override suspend fun getMyId(): Result<ContactId> = Result.success(0.toContactId())

    override suspend fun getContact(id: ContactId) = sampleContacts[id]
        ?.let { Result.success(it) }
        ?: Result.failure(Error("contact not found"))
}
