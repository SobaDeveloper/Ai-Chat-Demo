package com.example.domain.mapper

import com.example.data.service.firebase.dto.ChatMessageDto
import com.example.domain.models.firebase.ChatMessage

fun ChatMessageDto.toModel() = ChatMessage(
    text = content,
    role = role
)