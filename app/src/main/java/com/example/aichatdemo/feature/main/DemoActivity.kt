package com.example.aichatdemo.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.aichatdemo.feature.chat.ChatScreen
import com.example.aichatdemo.ui.theme.AiChatDemoTheme

class DemoActivity : ComponentActivity() {

    private val viewModel: DemoActivityViewModel by viewModel<DemoActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onActivityCreated()

        enableEdgeToEdge()
        setContent {
            AiChatDemoTheme {
                ChatScreen()

            }
        }

    }
}