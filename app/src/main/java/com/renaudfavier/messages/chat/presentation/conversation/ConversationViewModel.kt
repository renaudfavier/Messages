package com.renaudfavier.messages.chat.presentation.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaudfavier.messages.R
import com.renaudfavier.messages.chat.domain.Message
import com.renaudfavier.messages.chat.domain.MessageRepository
import com.renaudfavier.messages.chat.domain.ReadAllMessagesUseCase
import com.renaudfavier.messages.chat.presentation.conversation.mapper.ConversationListItemMapper
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationAction
import com.renaudfavier.messages.core.domain.ContactId
import com.renaudfavier.messages.core.domain.ContactRepository
import com.renaudfavier.messages.core.domain.toContactId
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.random.Random
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationUiModel as UiModel

@HiltViewModel(assistedFactory = ConversationViewModel.Factory::class)
class ConversationViewModel @AssistedInject constructor(
    @Assisted private val contactId: ContactId,
    private val contactRepository: ContactRepository,
    private val messageRepository: MessageRepository,
    private val readAllMessagesUseCase: ReadAllMessagesUseCase,
    private val mapper: ConversationListItemMapper,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(chatId: ContactId): ConversationViewModel
    }

    private val _uiState = MutableStateFlow<UiModel>(UiModel.Loading)
    val uiState = _uiState
        .onStart { loadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UiModel.Loading
        )

    private fun loadData() = viewModelScope.launch {
        readAllMessagesUseCase.execute(contactId)

        val contact = contactRepository.getContact(contactId).getOrNull()
        if (contact == null) {
            _uiState.value = UiModel.Error("Contact not found")
            return@launch
        }

        messageRepository.getConversationFlow(contactId)
            .map { messages ->
                UiModel.Content(
                    contactName = contact.name,
                    contactAvatar = R.drawable.ic_launcher_foreground,
                    message = "",
                    messages = messages.map { message: Message ->
                        mapper.map(message)
                    }.toPersistentList()
                )
            }
            .catch { e -> _uiState.value = UiModel.Error(e.message ?: "Unknown error") }
            .collect { _uiState.value = it }
    }

    fun onAction(action : ConversationAction) = when(action) {
        is ConversationAction.MessageChanged -> onMessageChanged(action)
        is ConversationAction.SendMessage -> onSendMessage(action)
    }.also { println(action) }

    private fun onMessageChanged(action: ConversationAction.MessageChanged) {
        val state = _uiState.value as? UiModel.Content ?: return
        _uiState.update { state.copy(message = action.message) }
    }

    private fun onSendMessage(action: ConversationAction.SendMessage) {
        if (action.message.isBlank()) return

        viewModelScope.launch {
            messageRepository.sendMessage(
                Message(
                    id = Random.nextInt(),
                    author = 0.toContactId(),
                    recipient = contactId,
                    date = Instant.now(),
                    content = action.message,
                    isUnread = true
                )
            )

            // Clear the message input after sending
            _uiState.update { state ->
                if (state is UiModel.Content) {
                    state.copy(message = "")
                } else {
                    state
                }
            }
        }
    }
}
