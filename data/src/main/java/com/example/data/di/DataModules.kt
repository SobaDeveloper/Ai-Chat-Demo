package com.example.data.di

import com.example.data.datasource.ChatLocalSource
import com.example.data.datasource.ChatRemoteSource
import com.example.data.db.AppDatabase
import com.example.data.db.dao.ChatMessageDao
import com.example.data.service.dummydata.DummyData
import com.example.data.service.gemini.GeminiService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule: Module
    get() = module {
        includes(serviceModule, databaseModule, dataSourceModule)
    }

val serviceModule = module {
    single { createService(get(), GeminiService::class.java) }
}

val databaseModule = module {
    single {
        AppDatabase.getDatabase(androidContext())
    }

    single<ChatMessageDao> {
        get<AppDatabase>().chatMessageDao()
    }
    singleOf(::DummyData)
}

val dataSourceModule = module {
    singleOf(::ChatLocalSource)
    singleOf(::ChatRemoteSource)
}