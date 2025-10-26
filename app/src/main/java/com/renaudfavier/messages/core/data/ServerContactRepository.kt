package com.renaudfavier.messages.core.data

import com.renaudfavier.messages.chat.data.api.MessagesApiService
import com.renaudfavier.messages.chat.data.api.mapper.ApiMapper
import com.renaudfavier.messages.core.domain.Contact
import com.renaudfavier.messages.core.domain.ContactId
import com.renaudfavier.messages.core.domain.ContactRepository
import com.renaudfavier.messages.core.domain.toContactId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerContactRepository @Inject constructor(
    private val apiService: MessagesApiService,
    private val apiMapper: ApiMapper
) : ContactRepository {

    private val contactsCache = mutableMapOf<ContactId, Contact>()

    override suspend fun getMyId(): Result<ContactId> {
        // User is always ID 0 in this system
        return Result.success(0.toContactId())
    }

    override suspend fun getContact(id: ContactId): Result<Contact> {
        return withContext(Dispatchers.IO) {
            try {
                // Check cache first
                contactsCache[id]?.let {
                    return@withContext Result.success(it)
                }

                // Load all chats from server
                val chats = apiService.getChats()
                val contacts = chats.map { apiMapper.mapChatToContact(it) }

                // Update cache
                contacts.forEach { contact ->
                    contactsCache[contact.id] = contact
                }

                // Return requested contact
                contactsCache[id]?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Contact not found"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
