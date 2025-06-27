package com.example.data.di

import com.example.data.network.DummyData
import com.example.data.service.gemini.GeminiService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single { createService(get(), GeminiService::class.java) }
    singleOf(::DummyData)
}