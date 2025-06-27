package com.example.domain.models.firebase

import com.example.data.service.gemini.dto.Role

data class ChatMessage(
    val text: String,
    val role: Role = Role.MODEL
)