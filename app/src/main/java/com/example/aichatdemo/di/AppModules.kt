package com.example.aichatdemo.di

import com.example.aichatdemo.feature.chat.ChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module
    get() = module {
        includes(viewModelModules)
    }

val viewModelModules = module {
    viewModelOf(::ChatViewModel)
}