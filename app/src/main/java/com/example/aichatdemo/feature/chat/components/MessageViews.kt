package com.example.aichatdemo.feature.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aichatdemo.ui.theme.LocalDimensions
import com.example.aichatdemo.ui.theme.LocalSpacing

@Composable
fun UserMessageView(message: String) {
    val spacing = LocalSpacing.current
    val dimensions = LocalDimensions.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = spacing.chatMessageIndent, end = spacing.sm, top = spacing.sm, bottom = spacing.sm),
        horizontalArrangement = Arrangement.End
    ) {
        Card(
            shape = RoundedCornerShape(dimensions.cornerRadiusLg),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.weight(1f, fill = false)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(spacing.md)
            )
        }
    }
}

@Composable
fun SystemMessageView(message: String) {
    val spacing = LocalSpacing.current
    val dimensions = LocalDimensions.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = spacing.sm, end = spacing.chatMessageIndent, top = spacing.xs, bottom = spacing.xs),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(dimensions.cornerRadiusLg),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.weight(1f, fill = false)
        ) {
            ModelResponseFormattedView(
                response = message
            )
        }
    }
}