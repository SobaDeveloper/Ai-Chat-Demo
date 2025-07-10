package com.example.aichatdemo.feature.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aichatdemo.utils.SettingsDataStore
import com.example.data.service.dummydata.DummyData
import com.example.data.service.gemini.dto.Role
import com.example.domain.models.firebase.ChatMessage
import com.example.domain.models.firebase.Conversation
import com.example.domain.usecase.GetChatMessagesBySession
import com.example.domain.usecase.GetConversations
import com.example.domain.usecase.GetFirebaseAiResponse
import com.example.domain.usecase.GetGeminiResponse
import com.example.domain.usecase.UpdateFirebaseAiPrompt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getConversations: GetConversations,
    private val getChatMessagesBySession: GetChatMessagesBySession,
    private val getGeminiResponse: GetGeminiResponse,
    private val getFirebaseAiResponse: GetFirebaseAiResponse,
    private val updateFirebaseAiPrompt: UpdateFirebaseAiPrompt,
    private val dataStore: SettingsDataStore,
    private val dummyData: DummyData
) : ViewModel() {

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> = _conversations

    private val _selectedSessionId = MutableStateFlow<String?>(null)
    val selectedSessionId: StateFlow<String?> = _selectedSessionId

    private val _messages = MutableStateFlow(initialList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    var tempPrompt = mutableStateOf("")
        private set

    val selectedModel: StateFlow<AiModel> = dataStore.selectedAiModel
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AiModel.FLASH_LITE_2_0
        )

    val aiPrompt: StateFlow<String> = dataStore.aiPrompt.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsDataStore.DEFAULT_PROMPT
    )

    fun getConversations() {
        viewModelScope.launch {
            val response = getConversations.invoke()
            _conversations.value = response
        }
    }

    fun getChatMessages(sessionId: String) {
        viewModelScope.launch {
            _selectedSessionId.value = sessionId
            getChatMessagesBySession(sessionId).collect {
                _messages.value = it
            }
        }
    }

    fun getModelResponse() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userInput = _inputText.value
                addChatMessage(ChatMessage(text = userInput, role = Role.USER))
                clearInput()
//                val geminiResponse = dummyData.dummyModelResponse
                val message = getChatMessage(userInput)
                addChatMessage(message)
            } catch (e: Exception) {
                val errorMessage = ChatMessage(text = "$e", role = Role.MODEL)
                addChatMessage(errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun getChatMessage(userInput: String): ChatMessage =
        when (selectedModel.value) {
            AiModel.FLASH_LITE_2_0 -> {
                val geminiResponse = getGeminiResponse(userInput).text
                ChatMessage(text = geminiResponse)
            }

            AiModel.FLASH_LIGHT_2_5 -> {
                val firebaseResponse = getFirebaseAiResponse(userInput)
                ChatMessage(text = firebaseResponse.text)
            }
        }

    private fun addChatMessage(message: ChatMessage) {
        _messages.update { currentMessages ->
            currentMessages + message
        }
    }

    fun updateAiModel(model: AiModel) {
        viewModelScope.launch {
            dataStore.setSelectedModel(model)
        }
    }

    fun setTempPrompt(value: String) {
        tempPrompt.value = value
    }

    fun updateAiPrompt() {
        viewModelScope.launch {
            dataStore.setAiPrompt(tempPrompt.value)
            updateFirebaseAiPrompt(tempPrompt.value)
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

enum class AiModel {
    FLASH_LITE_2_0,
    FLASH_LIGHT_2_5
}