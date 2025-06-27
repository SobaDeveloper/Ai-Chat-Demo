package com.example.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter(QUERY_NAME_KEY, apiKey)
            .build()

        val newRequest = chain.request().newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        const val QUERY_NAME_KEY = "key"
    }
}