package com.example.domain.di

import com.example.domain.repo.FirebaseRepository
import com.example.domain.repo.GeminiRepository
import com.example.domain.usecase.GetFirebaseAiResponse
import com.example.domain.usecase.GetGeminiResponse
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val repositoryModules = module {
    singleOf(::GeminiRepository)
    singleOf(::FirebaseRepository)
}

val useCaseModules = module {
    singleOf(::GetFirebaseAiResponse)
    singleOf(::GetGeminiResponse)
}