package com.example.domain.repo

import com.example.core.utils.SessionManager
import com.example.data.datasource.ChatLocalSource
import com.example.data.datasource.ChatRemoteSource
import com.example.data.db.entitty.ChatMessageEntity
import com.example.data.db.entitty.ConversationEntity
import com.example.data.service.gemini.dto.Role
import com.example.domain.mapper.toDto
import com.example.domain.mapper.toModel
import com.example.domain.models.firebase.ChatMessage
import com.example.domain.models.firebase.Conversation
import com.example.domain.models.gemini.ContentItem
import com.example.domain.models.gemini.GeminiRequest
import com.example.domain.models.gemini.PartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatRepository(
    private val remoteSource: ChatRemoteSource,
    private val localSource: ChatLocalSource,
    private val sessionManager: SessionManager
) {
    suspend fun getFirebaseResponse(input: String): ChatMessage {
        return getChatResponseAndStore(input) {
            remoteSource.getFirebaseResponse(input).toModel()
        }
    }

    suspend fun getGeminiResponse(input: String): ChatMessage {
        val request = GeminiRequest(
            contents = listOf(
                ContentItem(
                    parts = listOf(PartItem(text = input))
                )
            )
        ).toDto()
        return getChatResponseAndStore(input) {
            val geminiResponse = remoteSource.getGeminiResponse(requestDto = request).toModel()
            ChatMessage(text = geminiResponse.text)
        }
    }

    private suspend fun getChatResponseAndStore(
        input: String,
        fetchResponse: suspend (String) -> ChatMessage
    ): ChatMessage {

        val sessionId = sessionManager.getCurrentSessionId()

        // Store user message
        val userMessage = ChatMessageEntity(
            text = input,
            role = Role.USER,
            sessionId = sessionId
        )
        localSource.apply {
            insertMessage(userMessage)
        }

        val response = fetchResponse(input)

        // Store model message
        val modelMessage = ChatMessageEntity(
            text = response.text,
            sessionId = sessionId
        )
        localSource.insertMessage(modelMessage)

        return response
    }

    fun getMessagesByConversation(sessionId: String): Flow<List<ChatMessage>> =
        localSource.getMessagesBySession(sessionId)
            .map { list -> list.map { it.toModel() } }

    suspend fun getConversations(): List<Conversation> = localSource.getConversations().map { it.toModel() }

    suspend fun startNewConversation() {
        val sessionId = sessionManager.startNewSession()
        localSource.insertConversationIfNotExists(ConversationEntity(sessionId))
    }

    fun updateFirebasePrompt(prompt: String) = remoteSource.updateFirebasePrompt(prompt)
}