package com.example.aichatdemo.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.StartNewConversation
import kotlinx.coroutines.launch

class DemoActivityViewModel(
    private val startNewConversation: StartNewConversation
) : ViewModel() {

    fun onActivityCreated() {
        viewModelScope.launch {
            startNewConversation()
        }
    }
}