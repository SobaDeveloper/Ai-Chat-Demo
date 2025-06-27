package com.example.domain.mapper

import com.example.data.service.gemini.dto.ContentsDto
import com.example.data.service.gemini.dto.GeminiRequestDto
import com.example.data.service.gemini.dto.GeminiResponseDto
import com.example.data.service.gemini.dto.PartDto
import com.example.domain.models.gemini.ContentItem
import com.example.domain.models.gemini.GeminiRequest
import com.example.domain.models.gemini.GeminiResponse
import com.example.domain.models.gemini.PartItem

fun GeminiResponseDto.toNewModel(): GeminiResponse = GeminiResponse(
    responseId = responseId,
    text = candidates.first().content.parts.first().text,
    modelVersion = modelVersion,
    role = candidates.first().content.role,
    promptTokenCount = usageMetadata.promptTokenCount,
    candidatesTokenCount = usageMetadata.candidatesTokenCount,
    totalTokenCount = usageMetadata.totalTokenCount
)

fun GeminiRequest.toDto(): GeminiRequestDto = GeminiRequestDto(
    contents = contents.map { it.toDto() }
)

fun ContentItem.toDto(): ContentsDto = ContentsDto(
    parts = parts.map { it.toDto() }
)

fun PartItem.toDto(): PartDto = PartDto(
    text = text
)
