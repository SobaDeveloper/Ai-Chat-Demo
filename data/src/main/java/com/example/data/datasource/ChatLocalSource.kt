package com.example.data.datasource

import com.example.data.db.dao.ChatMessageDao
import com.example.data.db.entitty.ChatMessageEntity
import com.example.data.db.entitty.ConversationEntity
import kotlinx.coroutines.flow.Flow

class ChatLocalSource(
    private val chatMessageDao: ChatMessageDao
) {

    suspend fun insertConversationIfNotExists(conversation: ConversationEntity) {
        chatMessageDao.insertConversation(conversation)
    }

    suspend fun getConversations(): List<ConversationEntity> {
        return chatMessageDao.getConversations()
    }

    fun getMessagesBySession(sessionId: String): Flow<List<ChatMessageEntity>> {
        return chatMessageDao.getMessagesBySession(sessionId)
    }

    suspend fun insertMessage(message: ChatMessageEntity) {
        chatMessageDao.insertMessage(message)
    }

    suspend fun clearSession(sessionId: String) {
        chatMessageDao.clearSession(sessionId)
    }

    suspend fun clearAllMessages() {
        chatMessageDao.clearAllMessages()
    }
}