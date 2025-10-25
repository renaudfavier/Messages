package com.renaudfavier.messages.chat.presentation.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaudfavier.messages.R
import com.renaudfavier.messages.core.domain.ContactId
import com.renaudfavier.messages.chat.presentation.conversation.component.sampleMessages
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationAction
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationUiModel as UiModel

@HiltViewModel(assistedFactory = ConversationViewModel.Factory::class)
class ConversationViewModel @AssistedInject constructor(
    @Assisted private val contactId: ContactId,
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
        _uiState.update { UiModel.Loading }
        _uiState.update { UiModel.Content(
            contactName = "$contactId",
            contactAvatar = R.drawable.ic_launcher_foreground,
            message = "",
            messages = sampleMessages
        ) }
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
        val state = _uiState.value as? UiModel.Content ?: return
        if (action.message.isBlank()) return

        // TODO Send the message
    }
}
