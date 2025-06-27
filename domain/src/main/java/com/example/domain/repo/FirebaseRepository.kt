package com.example.domain.repo

import com.example.data.service.firebase.FirebaseAiService
import com.example.domain.mapper.toModel
import com.example.domain.models.firebase.ChatMessage

class FirebaseRepository(
    private val generativeAiModel: FirebaseAiService
) {
    suspend fun getResponse(prompt: String): ChatMessage = generativeAiModel.generateResponse(prompt).toModel()
}