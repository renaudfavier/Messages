@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.renaudfavier.messages.chat.presentation

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.renaudfavier.messages.chat.presentation.contacts.model.ContactListUiModel
import com.renaudfavier.messages.chat.presentation.contacts.Contacts
import com.renaudfavier.messages.chat.presentation.contacts.component.sampleContacts
import com.renaudfavier.messages.chat.presentation.conversation.Conversation
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Composable
fun Chat(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<ContactId>()

    NavigableListDetailPaneScaffold(
        navigator = scaffoldNavigator,
        listPane = {
            AnimatedPane {
                Contacts(
                    uiModel = ContactListUiModel.Content(sampleContacts),
                    onItemClick = { item ->
                        scope.launch {
                            scaffoldNavigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail,
                                item
                            )
                        }
                    },
                    innerPadding = innerPadding,
                )
            }
        },
        detailPane = {
            AnimatedPane {
                scaffoldNavigator.currentDestination?.contentKey?.let {
                    Conversation(
                        item = it,
                        innerPadding = innerPadding
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Parcelize
class ContactId(val id: Int) : Parcelable
