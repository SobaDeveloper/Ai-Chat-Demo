package com.example.domain.usecase

import com.example.domain.repo.ChatRepository

class UpdateFirebaseAiPrompt(
    private val repository: ChatRepository
) {
    operator fun invoke(prompt: String) = repository.updateFirebasePrompt(prompt)
}