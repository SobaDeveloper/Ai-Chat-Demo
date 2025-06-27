package com.example.data.di

import com.example.data.BuildConfig
import com.example.data.network.ApiKeyInterceptor
import com.example.data.service.firebase.FirebaseAiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single(named(Qualifiers.GEMINI_BASE_URL)) { provideGeminiBaseUrl() }
    single(named(Qualifiers.GEMINI_API_KEY)) { provideGeminiApiKey() }

    singleOf(::provideFirebaseAiModel)
    single { ApiKeyInterceptor(get(named(Qualifiers.GEMINI_API_KEY))) }
    singleOf(::provideMoshi)
    singleOf(::provideOkHttpClient)
    single {
        provideRetrofit(
            client = get(),
            moshi = get(),
            baseUrl = get(named(Qualifiers.GEMINI_BASE_URL))
        )
    }
}

private fun provideGeminiBaseUrl(): String = BuildConfig.GEMINI_BASE_URL

private fun provideGeminiApiKey(): String = BuildConfig.GEMINI_API_KEY

private fun provideFirebaseAiModel() = FirebaseAiService()

private fun provideMoshi(): Moshi =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private fun provideOkHttpClient(
    apiKeyInterceptor: ApiKeyInterceptor
): OkHttpClient {
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}

private fun provideRetrofit(
    client: OkHttpClient,
    moshi: Moshi,
    @Named(Qualifiers.GEMINI_BASE_URL) baseUrl: String
): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

fun <S> createService(retrofit: Retrofit, serviceClass: Class<S>): S = retrofit.create(serviceClass)

object Qualifiers {
    const val GEMINI_BASE_URL = "GeminiBaseUrl"
    const val GEMINI_API_KEY = "GeminiApiKey"
}