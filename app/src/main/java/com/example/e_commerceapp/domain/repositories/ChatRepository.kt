package com.example.e_commerceapp.domain.repositories

import com.example.e_commerceapp.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getMessages(ticketId: String): Flow<List<Message>>
    suspend fun sendMessage(message: Message,ticketId:String)
}