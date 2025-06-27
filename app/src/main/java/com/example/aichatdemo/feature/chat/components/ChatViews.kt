package com.example.aichatdemo.feature.chat.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aichatdemo.ui.components.DotsLoader
import com.example.aichatdemo.ui.theme.LocalSpacing
import com.example.core.utils.BlockType
import com.example.core.utils.parseModelResponse

@Composable
fun ModelResponseFormattedView(response: String) {
    val parsedBlocks = remember(response) { parseModelResponse(response) }
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.md)
    ) {
        for (block in parsedBlocks) {
            when (block.type) {
                BlockType.HEADER -> Text(
                    text = block.content,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = spacing.md, bottom = spacing.sm)
                )

                BlockType.BULLET -> Text(
                    text = "• ${block.content}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = spacing.sm, bottom = spacing.xs)
                )

                BlockType.NUMBERED -> Text(
                    text = block.content,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = spacing.xs)
                )

                BlockType.PARAGRAPH -> Text(
                    text = block.content,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = spacing.sm)
                )
            }
        }
    }
}

@Composable
fun UserInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .navigationBarsPadding(),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 56.dp, max = 120.dp),
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("Type something...") },
                maxLines = 4,
                singleLine = false
            )
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(onClick = onSend) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }
    }
}

@Preview
@Composable
fun showDotsLoader() {
    DotsLoader()
}