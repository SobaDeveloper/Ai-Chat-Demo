package com.example.data.service.firebase

import com.example.data.service.firebase.dto.ChatMessageDto
import com.example.data.service.gemini.dto.Role
import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content

class FirebaseAiService {

    private var model: GenerativeModel

    init {
        model = createModelWithSystemInstruction(DEFAULT_PROMPT)
    }

    private fun createModelWithSystemInstruction(prompt: String): GenerativeModel =
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(
                modelName = MODEL_GEMINI_FLASH_LITE,
                systemInstruction = content {
                    text(prompt)
                }
            )


    fun updatePrompt(prompt: String) {
        model = createModelWithSystemInstruction(prompt)
    }

    suspend fun generateResponse(input: String): ChatMessageDto {
        val response = model.generateContent(input)
        return ChatMessageDto(
            content = response.text ?: DEFAULT_RESPONSE,
            role = Role.MODEL
        )
    }

    companion object {
        const val MODEL_GEMINI_FLASH_LITE = "gemini-2.5-flash-lite-preview-06-17"
        const val DEFAULT_PROMPT = "You are an English language vocabulary instructor. You will help students define words and provide " +
                "three examples " +
                "of word usages."
        const val DEFAULT_RESPONSE = "No response"
    }
}