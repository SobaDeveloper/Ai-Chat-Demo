package com.example.domain.usecase

import com.example.domain.repo.ChatRepository

class StartNewConversation(
    private val repository: ChatRepository
) {
    suspend operator fun invoke() = repository.startNewConversation()
}