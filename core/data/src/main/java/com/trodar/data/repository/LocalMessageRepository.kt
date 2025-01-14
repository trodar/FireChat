package com.trodar.data.repository

import com.trodar.database.dao.MessageDao
import com.trodar.database.model.MessageEntity
import com.trodar.database.model.asMessageEntity
import com.trodar.database.model.asMessageModel
import com.trodar.firebase.model.MessageNetwork
import com.trodar.model.Chat
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMessageRepository @Inject constructor(
    private val messageDao: MessageDao,
) : MessageRepository {


    override fun getMessages(chatId: String): Flow<List<Chat>> = messageDao.getAllMessages(chatId)
            .map { it.map(MessageEntity::asMessageModel) }

    override suspend fun upsertMessage(chat: Chat) {
        messageDao.upsertMessage(chat.asMessageEntity())
    }
    override suspend fun upsertMessages(chat: List<MessageNetwork>, chatId: String) {
        messageDao.upsertMessages(chat.map { it.asMessageEntity(chatId) })
    }

    override suspend fun deleteMessages(ids: List<String>) = messageDao.deleteMessages(ids)

}