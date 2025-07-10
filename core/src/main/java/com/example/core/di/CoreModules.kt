package com.example.core.di

import com.example.core.utils.SessionManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModules = module {
    singleOf(::SessionManager)
}