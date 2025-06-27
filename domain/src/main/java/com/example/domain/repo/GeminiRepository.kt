package com.example.domain.repo

import com.example.data.service.gemini.GeminiService
import com.example.domain.mapper.toDto
import com.example.domain.mapper.toNewModel
import com.example.domain.models.gemini.ContentItem
import com.example.domain.models.gemini.GeminiRequest
import com.example.domain.models.gemini.GeminiResponse
import com.example.domain.models.gemini.PartItem

class GeminiRepository(
    private val geminiService: GeminiService
) {

    suspend fun getResponse(input: String): GeminiResponse {
        val request = GeminiRequest(
            contents = listOf(
                ContentItem(
                    parts = listOf(PartItem(text = input))
                )
            )
        ).toDto()
        return geminiService.getGeminiResponse(request = request).toNewModel()
    }
}