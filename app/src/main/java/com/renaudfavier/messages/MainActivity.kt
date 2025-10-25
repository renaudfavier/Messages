package com.renaudfavier.messages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.renaudfavier.messages.chat.presentation.Chat
import com.renaudfavier.messages.core.ui.theme.MessagesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MessagesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Chat(innerPadding)
                }
            }
        }
    }
}
