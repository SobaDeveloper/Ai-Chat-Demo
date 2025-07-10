package com.example.domain.usecase

import com.example.domain.models.firebase.Conversation
import com.example.domain.repo.ChatRepository

class GetConversations(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(): List<Conversation> = repository.getConversations()
}