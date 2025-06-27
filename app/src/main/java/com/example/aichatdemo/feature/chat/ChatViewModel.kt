package com.example.aichatdemo.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.network.DummyData
import com.example.data.service.gemini.dto.Role
import com.example.domain.models.firebase.ChatMessage
import com.example.domain.usecase.GetFirebaseAiResponse
import com.example.domain.usecase.GetGeminiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getGeminiResponse: GetGeminiResponse,
    private val getFirebaseAiResponse: GetFirebaseAiResponse,
    private val dummyData: DummyData
) : ViewModel() {

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _messages = MutableStateFlow(initialList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    fun getModelResponse() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userInput = _inputText.value
                addChatMessage(ChatMessage(text = userInput, role = Role.USER))
                clearInput()
                val firebaseResponse = getFirebaseAiResponse(userInput)
//                val geminiResponse = getGeminiResponse(_inputText.value)
                val chatMessage = ChatMessage(text = firebaseResponse.text)
//                val geminiResponse = dummyData.dummyModelResponse
//                val chatMessage = ChatMessage(text = geminiResponse)
                addChatMessage(chatMessage)
            } catch (e: Exception) {
                val errorMessage = ChatMessage(text = "$e", role = Role.MODEL)
                addChatMessage(errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun addChatMessage(message: ChatMessage) {
        _messages.update { currentMessages ->
            currentMessages + message
        }
    }

    fun onInputChange(input: String) {
        _inputText.value = input
    }

    private fun clearInput() {
        _inputText.value = ""
    }

    private fun initialList() = listOf(ChatMessage(text = dummyData.defaultPrompt))
}