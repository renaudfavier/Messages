package com.renaudfavier.messages.chat.domain

import com.renaudfavier.messages.core.domain.ContactId
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun getAllConversations(): Flow<List<ContactId>>
    fun getConversationFlow(contactId: ContactId): Flow<List<Message>>

    suspend fun sendMessage(message: Message): Result<Unit>
    suspend fun messageWasRead(id: MessageId): Result<Unit>

}