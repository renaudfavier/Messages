package com.renaudfavier.messages.chat.presentation.conversation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.renaudfavier.messages.R
import com.renaudfavier.messages.core.ui.theme.MessagesTheme

@Composable
fun ConversationInputBar(
    message: String,
    onAnswerChange: (String) -> Unit,
    onSendButtonTap: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                text = message,
                selection = TextRange(message.length)
            )
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            onAnswerChange(it.text)
        },
        placeholder = {
            Text(stringResource(R.string.inputPlaceholder))
        },
        trailingIcon = {
            IconButton(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(100)
                    )
                    ,
                onClick = {
                    onSendButtonTap(message)
                    focusRequester.freeFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = stringResource(R.string.sendButtonContentDescription),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        shape = RoundedCornerShape(100),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions {
            onSendButtonTap(message)
            focusRequester.freeFocus()
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
    )
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun InputBarPrev() {
    MessagesTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            ConversationInputBar(
                message = "so fasttttt",
                onAnswerChange = {},
                onSendButtonTap = {},
            )
            ConversationInputBar(
                message = "",
                onAnswerChange = {},
                onSendButtonTap = {},
            )
            ConversationInputBar(
                message = "Interface is operating with a small team of dedicated & talented people. We are looking for seasoned engineers with a deep technical knowledge, strong understanding of their technical stack, and excellent product intuitions to join our team.\n" +
                        "\n" +
                        "This exercise has been designed to give a glimpse of what it is like to build a messaging app, and the kind of technical challenges we face",
                onAnswerChange = {},
                onSendButtonTap = {},
            )
        }
    }
}
