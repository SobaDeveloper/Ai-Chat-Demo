package com.example.domain.usecase

import com.example.domain.models.firebase.ChatMessage
import com.example.domain.repo.FirebaseRepository

class GetFirebaseAiResponse(
    private val repository: FirebaseRepository
) {
    suspend operator fun invoke(input: String): ChatMessage = repository.getResponse(input)
}