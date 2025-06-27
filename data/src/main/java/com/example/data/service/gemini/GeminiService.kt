package com.example.data.service.gemini

import com.example.data.service.gemini.dto.GeminiRequestDto
import com.example.data.service.gemini.dto.GeminiResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface GeminiService {

    @POST("v1beta/models/gemini-2.0-flash-lite:generateContent")
    suspend fun getGeminiResponse(@Body request: GeminiRequestDto): GeminiResponseDto
}