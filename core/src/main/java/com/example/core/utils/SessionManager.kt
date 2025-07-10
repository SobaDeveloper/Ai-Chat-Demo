package com.example.core.utils

class SessionManager {
    private var currentSessionId: String = generateSessionId()

    fun getCurrentSessionId(): String = currentSessionId

    fun startNewSession(): String {
        currentSessionId = generateSessionId()
        return currentSessionId
    }

    private fun generateSessionId(): String = System.currentTimeMillis().toString()
}