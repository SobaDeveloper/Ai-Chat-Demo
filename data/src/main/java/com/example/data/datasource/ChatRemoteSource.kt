package com.example.data.datasource

import com.example.data.service.firebase.FirebaseAiService
import com.example.data.service.firebase.dto.ChatMessageDto
import com.example.data.service.gemini.GeminiService
import com.example.data.service.gemini.dto.GeminiRequestDto
import com.example.data.service.gemini.dto.GeminiResponseDto

class ChatRemoteSource(
    private val firebaseAiService: FirebaseAiService,
    private val geminiService: GeminiService
) {

    suspend fun getFirebaseResponse(prompt: String): ChatMessageDto = firebaseAiService.generateResponse(prompt)

    suspend fun getGeminiResponse(requestDto: GeminiRequestDto): GeminiResponseDto = geminiService.getGeminiResponse(requestDto)

    fun updateFirebasePrompt(prompt: String) = firebaseAiService.updatePrompt(prompt)
}