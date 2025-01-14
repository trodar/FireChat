package com.trodar.data.repository

import com.trodar.firebase.model.MessageNetwork
import com.trodar.model.Chat
import kotlinx.coroutines.flow.Flow

interface MessageRepository  {

    fun getMessages(chatId: String): Flow<List<Chat>>

    suspend fun upsertMessage(chat: Chat)
    suspend fun upsertMessages(chat: List<MessageNetwork>, chatId: String)

    suspend fun deleteMessages(ids: List<String>)
}