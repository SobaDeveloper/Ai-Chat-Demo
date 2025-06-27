package com.example.data.service.firebase.dto

import com.example.data.service.gemini.dto.Role

data class ChatMessageDto(
    val content: String,
    val role: Role
)