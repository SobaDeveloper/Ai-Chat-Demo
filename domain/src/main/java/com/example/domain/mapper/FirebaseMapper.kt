package com.example.domain.mapper

import com.example.data.db.entitty.ChatMessageEntity
import com.example.data.db.entitty.ConversationEntity
import com.example.data.service.firebase.dto.ChatMessageDto
import com.example.domain.models.firebase.ChatMessage
import com.example.domain.models.firebase.Conversation

fun ChatMessageDto.toModel() = ChatMessage(
    text = content,
    role = role
)

fun ChatMessageEntity.toModel() = ChatMessage(
    text = text,
    role = role
)

fun ConversationEntity.toModel() = Conversation(
    id = id,
    createdAt = createdAt
)