package com.example.domain.usecase

import com.example.domain.models.firebase.ChatMessage
import com.example.domain.repo.ChatRepository

class GetFirebaseAiResponse(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(input: String): ChatMessage = repository.getFirebaseResponse(input)
}