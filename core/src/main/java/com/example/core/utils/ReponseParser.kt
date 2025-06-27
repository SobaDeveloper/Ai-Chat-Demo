package com.example.core.utils

data class ParagraphBlock(
    val type: BlockType,
    val content: String
)

enum class BlockType {
    HEADER,
    BULLET,
    NUMBERED,
    PARAGRAPH
}

fun parseModelResponse(raw: String): List<ParagraphBlock> {
    val cleaned = raw.replace("\\n", "\n")
    val lines = cleaned.split("\n")

    return lines.mapNotNull { line ->
        val trimmed = line.trim()
        when {
            trimmed.startsWith("**") && trimmed.endsWith("**") ->
                ParagraphBlock(BlockType.HEADER, trimmed.removeSurrounding("**"))

            trimmed.startsWith("*") -> {
                val bulletText = trimmed.removePrefix("*").trimStart()
                val formatted = formatInlineMarkdown(bulletText)
                ParagraphBlock(BlockType.BULLET, formatted)
            }

            Regex("^\\d+\\.\\s+.*").matches(trimmed) ->
                ParagraphBlock(BlockType.NUMBERED, trimmed)

            trimmed.isNotEmpty() ->
                ParagraphBlock(BlockType.PARAGRAPH, trimmed)

            else -> null
        }
    }
}

fun formatInlineMarkdown(text: String): String = text.replace(Regex("\\*\\*(.*?)\\*\\*")) { matchResult ->
    matchResult.groupValues[1]
}