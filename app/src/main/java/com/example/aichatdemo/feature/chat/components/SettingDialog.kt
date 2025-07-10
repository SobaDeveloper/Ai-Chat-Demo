package com.example.aichatdemo.feature.chat.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aichatdemo.R
import com.example.aichatdemo.feature.chat.AiModel
import com.example.aichatdemo.feature.chat.ChatViewModel

@Composable
fun AiModelDialog(
    viewModel: ChatViewModel,
    onDismissRequest: () -> Unit
) {
    val selectedModel by viewModel.selectedModel.collectAsState()
    val aiPrompt by viewModel.aiPrompt.collectAsState()
    val tempPrompt by remember { viewModel.tempPrompt }

    LaunchedEffect(Unit) {
        viewModel.setTempPrompt(aiPrompt)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.ok))
            }
        },
        title = { Text(stringResource(R.string.choose_ai_model)) },
        text = {
            Column {
                RadioOption(
                    text = "Gemini API - Gemini 2.0 Flash Lite (no prompt)",
                    selected = selectedModel == AiModel.FLASH_LITE_2_0,
                    onClick = { viewModel.updateAiModel(AiModel.FLASH_LITE_2_0) }
                )
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.updateAiModel(AiModel.FLASH_LIGHT_2_5)
                            }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedModel == AiModel.FLASH_LIGHT_2_5,
                            onClick = { viewModel.updateAiModel(AiModel.FLASH_LIGHT_2_5) }
                        )
                        Text(
                            text = "Firebase - Gemini 2.5 Flash Lite",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                if (selectedModel == AiModel.FLASH_LIGHT_2_5) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = tempPrompt,
                            onValueChange = { viewModel.setTempPrompt(it) },
                            label = { Text(stringResource(R.string.system_prompt)) },
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { viewModel.updateAiPrompt() }) {
                            Text(stringResource(R.string.save))
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun RadioOption(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text, Modifier.padding(start = 8.dp))
    }
}