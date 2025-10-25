package com.renaudfavier.messages.chat.domain

import com.renaudfavier.messages.core.domain.Contact
import com.renaudfavier.messages.core.domain.ContactRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class ConversationSummary(
    val contact: Contact,
    val lastMessage: Message
)

class GetConversationListUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val contactRepository: ContactRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun execute(): Flow<List<ConversationSummary>> = messageRepository.getAllConversations()
        .flatMapLatest { contactIds ->
            if (contactIds.isEmpty()) return@flatMapLatest flowOf(emptyList())

            val conversationFlows = contactIds.map { contactId ->
                messageRepository.getConversationFlow(contactId)
                    .map { messages ->
                        messages.maxByOrNull { it.date }?.let { lastMessage ->
                            val contact = contactRepository.getContact(contactId)
                                .getOrElse { return@map null  }

                            ConversationSummary(contact, lastMessage)
                        }
                    }
            }

            combine(conversationFlows) { summaries ->
                summaries.filterNotNull()
                    .sortedByDescending { it.lastMessage.date }
            }
        }
}
