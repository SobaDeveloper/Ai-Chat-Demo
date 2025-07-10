package com.example.aichatdemo.di

import com.example.aichatdemo.feature.chat.ChatViewModel
import com.example.aichatdemo.feature.main.DemoActivity
import com.example.aichatdemo.feature.main.DemoActivityViewModel
import com.example.aichatdemo.utils.SettingsDataStore
import com.example.core.utils.SessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module
    get() = module {
        includes(viewModelModules, dataStoreModules)
        scope<DemoActivity> {
            scoped { SessionManager() }
            viewModel { DemoActivityViewModel(get()) }
        }
    }

val viewModelModules = module {
    viewModelOf(::ChatViewModel)
    viewModelOf(::DemoActivityViewModel)
}

val dataStoreModules = module {
    single { SettingsDataStore(androidContext()) }
}