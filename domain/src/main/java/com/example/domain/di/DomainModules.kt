package com.example.domain.di

import com.example.domain.repo.ChatRepository
import com.example.domain.usecase.GetChatMessagesBySession
import com.example.domain.usecase.GetConversations
import com.example.domain.usecase.GetFirebaseAiResponse
import com.example.domain.usecase.GetGeminiResponse
import com.example.domain.usecase.StartNewConversation
import com.example.domain.usecase.UpdateFirebaseAiPrompt
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val repositoryModules = module {
    singleOf(::ChatRepository)
}

val useCaseModules = module {
    singleOf(::GetFirebaseAiResponse)
    singleOf(::GetGeminiResponse)
    singleOf(::GetConversations)
    singleOf(::GetChatMessagesBySession)
    singleOf(::StartNewConversation)
    singleOf(::UpdateFirebaseAiPrompt)
}