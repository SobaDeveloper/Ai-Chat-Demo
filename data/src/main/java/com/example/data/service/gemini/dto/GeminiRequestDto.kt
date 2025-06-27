package com.example.data.service.gemini.dto

import com.squareup.moshi.Json

data class GeminiRequestDto(
    @Json(name = "contents") val contents: List<ContentsDto>
)

data class ContentsDto(
    @Json(name = "parts") val parts: List<PartDto>
)