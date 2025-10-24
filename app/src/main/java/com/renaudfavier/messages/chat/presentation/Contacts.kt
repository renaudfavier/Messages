package com.renaudfavier.messages.chat.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Contacts(
    onItemClick: (ContactId) -> Unit,
) {
    LazyColumn {
        listOf(1,2,3,4).forEach { id ->
            item {
                ListItem(
                    modifier = Modifier
                        .background(Color.Magenta)
                        .clickable {
                            onItemClick(ContactId(id))
                        },
                    headlineContent = {
                        Text(
                            text = "user with id $id",
                        )
                    },
                )
            }
        }
    }
}
