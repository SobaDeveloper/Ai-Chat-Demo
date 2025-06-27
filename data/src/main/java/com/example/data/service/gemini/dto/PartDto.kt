package com.example.data.service.gemini.dto

import com.squareup.moshi.Json

data class PartDto(
    @Json(name = "text") val text: String
)