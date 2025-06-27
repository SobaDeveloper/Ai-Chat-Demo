package com.example.aichatdemo.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimensions(
    val cornerRadiusSm: Dp = 4.dp,
    val cornerRadiusMd: Dp = 8.dp,
    val cornerRadiusLg: Dp = 12.dp,
    val cornerRadiusXl: Dp = 16.dp,
)

val LocalDimensions = staticCompositionLocalOf { Dimensions() }