package com.example.data.service.gemini.dto

import com.squareup.moshi.Json

data class GeminiResponseDto(
    @Json(name = "candidates") val candidates: List<CandidateDto>,
    @Json(name = "modelVersion") val modelVersion: String,
    @Json(name = "usageMetadata") val usageMetadata: UsageMetadataDto,
    @Json(name = "responseId") val responseId: String
)

data class UsageMetadataDto(
    @Json(name = "candidatesTokenCount") val candidatesTokenCount: Int,
    @Json(name = "promptTokensDetails") val promptTokensDetails: List<PromptTokensDetailDto>,
    @Json(name = "totalTokenCount") val totalTokenCount: Int,
    @Json(name = "promptTokenCount") val promptTokenCount: Int,
    @Json(name = "candidatesTokensDetails") val candidatesTokensDetails: List<CandidatesTokensDetailDto>
)

data class PromptTokensDetailDto(
    @Json(name = "modality") val modality: String,
    @Json(name = "tokenCount") val tokenCount: Int
)

data class ContentDto(
    @Json(name = "role") val role: Role,
    @Json(name = "parts") val parts: List<PartDto>
)

data class CandidatesTokensDetailDto(
    @Json(name = "modality") val modality: String,
    @Json(name = "tokenCount") val tokenCount: Int
)

data class CandidateDto(
    @Json(name = "avgLogprobs") val avgLogprobs: Any,
    @Json(name = "finishReason") val finishReason: String,
    @Json(name = "content") val content: ContentDto
)

enum class Role {
    @Json(name = "user")
    USER,
    @Json(name = "model")
    MODEL
}