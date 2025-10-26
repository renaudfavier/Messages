package com.renaudfavier.messages.chat.presentation.contacts

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.renaudfavier.messages.core.domain.ContactId
import com.renaudfavier.messages.chat.presentation.contacts.component.ContactListItem
import com.renaudfavier.messages.chat.presentation.contacts.component.sampleContactsListItemUiModel
import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListAction
import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListUiModel
import com.renaudfavier.messages.core.presentation.ErrorBox
import com.renaudfavier.messages.core.ui.theme.MessagesTheme

@Composable
fun Contacts(
    onItemClick: (ContactId) -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,

    ) {
    val viewModel: ContactsViewModel = hiltViewModel()
    val uiModel by viewModel.uiState.collectAsStateWithLifecycle()

    Contacts(
        uiModel = uiModel,
        onAction = viewModel::onAction,
        onContactClick = { onItemClick(it) },
        innerPadding,
        modifier
    )
}

@Composable
private fun Contacts(
    uiModel: ContactListUiModel,
    onAction: (ContactListAction) -> Unit,
    onContactClick: (ContactId) -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    when (uiModel) {
        is ContactListUiModel.Content -> Content(uiModel, onContactClick, innerPadding, modifier)
        ContactListUiModel.Loading -> Loading(modifier)
        is ContactListUiModel.Error -> ErrorBox(
            message = uiModel.message,
            modifier = modifier,
            onRetry = { onAction(ContactListAction.Retry) }
        )
    }
}

@Composable
fun Content(
    uiModel: ContactListUiModel.Content,
    onItemClick: (ContactId) -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current
    val combinedPadding = PaddingValues(
        start = innerPadding.calculateStartPadding(layoutDirection) + 24.dp,
        top = innerPadding.calculateTopPadding() + 24.dp,
        end = innerPadding.calculateEndPadding(layoutDirection) + 24.dp,
        bottom = innerPadding.calculateBottomPadding()
    )

    LazyColumn(
        modifier = modifier,
        contentPadding = combinedPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "chats",
                modifier = modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.displayMedium
            )
        }
        items(items = uiModel.contacts) {
            ContactListItem(
                contact = it,
                modifier = Modifier.clickable {
                    onItemClick(it.contactId)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
private fun ContactsPrev() {
    MessagesTheme {
        Scaffold { innerPadding ->
            Content(
                uiModel = ContactListUiModel.Content(
                    contacts = sampleContactsListItemUiModel
                ),
                onItemClick = {},
                innerPadding = innerPadding,
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@PreviewScreenSizes
@Composable
private fun ContactsLoadingPrev() {
    MessagesTheme {
        Scaffold {
            Loading()
        }
    }
}