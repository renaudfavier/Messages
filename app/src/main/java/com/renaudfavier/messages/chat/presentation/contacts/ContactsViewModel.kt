package com.renaudfavier.messages.chat.presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaudfavier.messages.chat.domain.GetConversationListUseCase
import com.renaudfavier.messages.chat.presentation.contacts.mapper.ContactListUiModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListUiModel as UiModel

@HiltViewModel
class ContactsViewModel @Inject constructor(
    getConversationListUseCase: GetConversationListUseCase,
    val mapper: ContactListUiModelMapper,
) : ViewModel() {

    val uiState: StateFlow<UiModel> = getConversationListUseCase.execute()
        .map { summaries ->
            UiModel.Content(
                contacts = mapper.map(summaries).toPersistentList()
            ) as UiModel
        }
        .onStart { emit(UiModel.Loading) }
        .catch { e -> emit(UiModel.Error(e.message ?: "Unknown error")) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UiModel.Loading
        )

}
