package com.example.aichatdemo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun DotsLoader(
    modifier: Modifier = Modifier,
    dotCount: Int = 3,
    delayMillis: Int = 300,
    dot: String = "●"
) {
    var visibleDots by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(delayMillis.toLong())
            visibleDots = (visibleDots + 1) % (dotCount + 1)
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = buildString {
            repeat(visibleDots) { append(dot) }
        })
    }
}