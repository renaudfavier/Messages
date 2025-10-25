@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.renaudfavier.messages.chat.presentation.conversation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.renaudfavier.messages.R
import com.renaudfavier.messages.chat.presentation.conversation.component.ConversationContactBadge
import com.renaudfavier.messages.chat.presentation.conversation.component.ConversationInputBar
import com.renaudfavier.messages.chat.presentation.conversation.component.ConversationMessageList
import com.renaudfavier.messages.chat.presentation.conversation.component.sampleMessages
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationAction
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationUiModel
import com.renaudfavier.messages.core.domain.ContactId
import com.renaudfavier.messages.core.ui.theme.MessagesTheme
import kotlinx.coroutines.launch

@Composable
fun Conversation(
    contactId: ContactId,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    key(contactId) {
        val viewModel = hiltViewModel(
            key = "${contactId.id}",
            creationCallback = { factory: ConversationViewModel.Factory ->
                factory.create(contactId)
            }
        )

        val uiModel by viewModel.uiState.collectAsStateWithLifecycle()

        Conversation(
            uiModel = uiModel,
            onAction = viewModel::onAction,
            innerPadding = innerPadding,
            modifier = modifier
        )
    }
}

@Composable
private fun Conversation(
    uiModel: ConversationUiModel,
    onAction: (ConversationAction) -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    when(uiModel) {
        is ConversationUiModel.Content -> Content(uiModel, onAction, innerPadding, modifier)
        ConversationUiModel.Loading -> Loading(modifier = modifier)
        is ConversationUiModel.Error -> Text(uiModel.message)
    }
}

@Composable
private fun Content(
    uiModel: ConversationUiModel.Content,
    onAction: (ConversationAction) -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) = with(uiModel) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isFirstComposition = remember { mutableStateOf(true) }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty() && !isFirstComposition.value) {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        }
        isFirstComposition.value = false
    }

    Box(modifier.padding(innerPadding)) {
        Column(Modifier.fillMaxSize()) {
            ConversationMessageList(
                messages,
                listState = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
            )
            ConversationInputBar(
                message = message,
                onAnswerChange = { onAction(ConversationAction.MessageChanged(it)) },
                onSendButtonTap = { onAction(ConversationAction.SendMessage(it)) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )
        }
        ConversationContactBadge(
            name = contactName,
            avatarRes = contactAvatar,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularWavyProgressIndicator()
    }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun ConversationContentPrev() {
    MessagesTheme {
        Scaffold { innerPadding ->
            Conversation(
                uiModel = ConversationUiModel.Content(
                    contactName = "John",
                    contactAvatar = R.drawable.ic_launcher_foreground,
                    message = "",
                    messages = sampleMessages
                ),
                onAction = {},
                innerPadding = innerPadding
            )
        }
    }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun ConversationLoadingPrev() {
    MessagesTheme {
        Scaffold { innerPadding ->
            Conversation(
                uiModel = ConversationUiModel.Loading,
                onAction = {},
                innerPadding = innerPadding
            )
        }
    }
}