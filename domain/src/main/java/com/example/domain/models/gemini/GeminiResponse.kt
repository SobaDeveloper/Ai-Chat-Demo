package com.example.domain.models.gemini

import com.example.data.service.gemini.dto.Role

data class GeminiResponse(
    val responseId: String,
    val text: String,
    val modelVersion: String,
    val role: Role,
    val promptTokenCount: Int,
    val candidatesTokenCount: Int,
    val totalTokenCount: Int,
)


