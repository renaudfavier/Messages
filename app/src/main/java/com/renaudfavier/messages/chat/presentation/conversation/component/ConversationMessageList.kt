package com.renaudfavier.messages.chat.presentation.conversation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel.MessageUiModel
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel.MessageUiModel.EmojiMessage
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel.MessageUiModel.TextMessage
import com.renaudfavier.messages.chat.presentation.conversation.model.ConversationListItemUiModel.TimeUiModel
import com.renaudfavier.messages.core.ui.theme.MessagesTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ConversationMessageList(
    items: PersistentList<ConversationListItemUiModel>,
    modifier: Modifier = Modifier,
    onListTap : () -> Unit = {},
    listState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onListTap()
            },
        reverseLayout = true,
        contentPadding = PaddingValues(top = 80.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom),
    ) {
        items(
            items = items.asReversed(),
            key = { it.id }
        ) { item ->
            when (item) {
                is TimeUiModel -> TimeItem(item)
                is MessageUiModel -> {
                    MessageRow(item.isMine) {
                        when (item) {
                            is TextMessage -> TextMessageItem(item)
                            is EmojiMessage -> EmojiMessageItem(item.emoji)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TimeItem(item: TimeUiModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun TextMessageItem(item: TextMessage) {
    val backgroundColor =
        if (item.isMine) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant

    val textColor =
        if (item.isMine) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = item.text,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Composable
private fun EmojiMessageItem(emoji: String) {
    Text(
        text = emoji,
        fontSize = 40.sp,
        modifier = Modifier.padding(4.dp)
    )
}

@Composable
private fun MessageRow(
    isMine: Boolean,
    content: @Composable () -> Unit
) = Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
) {
    content()
}

@PreviewLightDark
@Composable
private fun ConversationMessageListPrev() {
    MessagesTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.surface)) {
            ConversationMessageList(sampleMessages)
        }
    }
}

@PreviewLightDark
@Composable
private fun ConversationMessageListShortPrev() {
    MessagesTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.surface)) {
            ConversationMessageList(sampleMessages.take(5).toPersistentList())
        }
    }
}

