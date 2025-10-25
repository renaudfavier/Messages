package com.renaudfavier.messages.chat.presentation.conversation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.renaudfavier.messages.chat.presentation.ContactId

@Composable
fun Conversation(item: ContactId) {
    Card {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Details page for ${item.id}",
                fontSize = 24.sp,
            )
        }
    }
}
