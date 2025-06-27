package com.example.domain.models.gemini

data class GeminiRequest(
    val contents: List<ContentItem>
)

data class PartItem(
    val text: String
)

data class ContentItem(
    val parts: List<PartItem>
)
