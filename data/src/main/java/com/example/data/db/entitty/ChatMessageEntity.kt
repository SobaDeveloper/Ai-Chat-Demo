package com.example.data.db.entitty

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.service.gemini.dto.Role

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val role: Role = Role.MODEL,
    val sessionId: String,
    val timestamp: Long = System.currentTimeMillis()
)