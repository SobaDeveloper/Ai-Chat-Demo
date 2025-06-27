package com.example.domain.usecase

import com.example.domain.models.gemini.GeminiResponse
import com.example.domain.repo.GeminiRepository

class GetGeminiResponse(
    private val repository: GeminiRepository
) {
    suspend operator fun invoke(input: String): GeminiResponse = repository.getResponse(input)
}