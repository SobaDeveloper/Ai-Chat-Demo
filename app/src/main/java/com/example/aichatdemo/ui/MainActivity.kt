package com.example.aichatdemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.aichatdemo.feature.chat.ChatScreen
import com.example.aichatdemo.ui.theme.AiChatDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AiChatDemoTheme {
                ChatScreen()
            }
        }
    }
}