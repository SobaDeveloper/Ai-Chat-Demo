package com.example.domain.usecase

import com.example.domain.models.firebase.ChatMessage
import com.example.domain.repo.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatMessagesBySession(
    private val repository: ChatRepository
) {
    operator fun invoke(sessionId: String): Flow<List<ChatMessage>> = repository.getMessagesByConversation(sessionId)
}