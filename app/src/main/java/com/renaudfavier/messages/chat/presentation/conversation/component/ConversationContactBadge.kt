package com.renaudfavier.messages.chat.presentation.conversation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renaudfavier.messages.R
import com.renaudfavier.messages.core.ui.theme.MessagesTheme

@Composable
fun ConversationContactBadge(
    name: String,
    avatarRes: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(52.dp),
        shadowElevation = 8.dp,
        tonalElevation = 1.dp,
        shape = RoundedCornerShape(100),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = avatarRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )

            Text(
                text = name,
                modifier = Modifier
                    .padding(end = 12.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}

@Preview
@Composable
private fun ConversationContactBadgePrev() {
    MessagesTheme {
        Box(Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)) {
            ConversationContactBadge("John", R.drawable.ic_launcher_foreground)
        }
    }
}
