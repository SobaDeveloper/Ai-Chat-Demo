package com.example.aichatdemo

import android.app.Application
import com.example.aichatdemo.di.appModule
import com.example.core.di.coreModules
import com.example.data.di.dataModule
import com.example.data.di.networkModule
import com.example.domain.di.repositoryModules
import com.example.domain.di.useCaseModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AiChatDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AiChatDemoApplication)
            modules(
                listOf(
                    appModule,
                    coreModules,
                    networkModule,
                    dataModule,
                    repositoryModules,
                    useCaseModules
                )
            )
        }
    }
}