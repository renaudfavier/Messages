@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.renaudfavier.messages.chat.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.renaudfavier.messages.chat.presentation.contacts.Contacts
import com.renaudfavier.messages.chat.presentation.conversation.Conversation
import com.renaudfavier.messages.core.domain.ContactId
import kotlinx.coroutines.launch

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
                val isListFullScreen = scaffoldNavigator.scaffoldValue.secondary == PaneAdaptedValue.Hidden
                val listPadding =
                    if (isListFullScreen) { innerPadding }
                    else {
                        PaddingValues(
                            start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                            top = innerPadding.calculateTopPadding(),
                            end = 0.dp,
                            bottom = innerPadding.calculateBottomPadding()
                        )
                    }

                Contacts(
                    onItemClick = { item ->
                        scope.launch {
                            scaffoldNavigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail,
                                item
                            )
                        }
                    },
                    innerPadding = listPadding,
                )
            }
        },
        detailPane = {
            AnimatedPane {
                scaffoldNavigator.currentDestination?.contentKey?.let {
                    val isDetailsFullScreen = scaffoldNavigator.scaffoldValue.primary == PaneAdaptedValue.Hidden
                    val detailPadding =
                        if(isDetailsFullScreen) { innerPadding }
                        else {
                            PaddingValues(
                                start = 0.dp,
                                top = innerPadding.calculateTopPadding(),
                                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                                bottom = innerPadding.calculateBottomPadding()
                            )
                        }

                    Conversation(
                        contactId = it,
                        innerPadding = detailPadding
                    )
                }
            }
        },
        modifier = modifier
    )
}

