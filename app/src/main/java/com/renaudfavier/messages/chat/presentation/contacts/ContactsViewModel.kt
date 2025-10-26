package com.renaudfavier.messages.chat.presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaudfavier.messages.chat.domain.GetConversationListUseCase
import com.renaudfavier.messages.chat.presentation.contacts.mapper.ContactListUiModelMapper
import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListUiModel as UiModel

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getConversationListUseCase: GetConversationListUseCase,
    val mapper: ContactListUiModelMapper,
) : ViewModel() {

    private val retryTrigger = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit) // Initial load
    }

    val uiState: StateFlow<UiModel> = retryTrigger
        .flatMapLatest {
            getConversationListUseCase.execute()
                .map { summaries ->
                    UiModel.Content(
                        contacts = mapper.map(summaries).toPersistentList()
                    ) as UiModel
                }
                .onStart { emit(UiModel.Loading) }
                .catch { e ->
                    println(e.message)
                    emit(UiModel.Error(e.message ?: "Unknown error"))
                }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UiModel.Loading
        )

    fun onAction(action: ContactListAction) =
        when(action) {
            ContactListAction.Retry -> retry()
        }.also { println(action) }

    private fun retry() = viewModelScope.launch {
        retryTrigger.emit(Unit)
    }

}
