package com.trodar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.trodar.database.model.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("Select * from message where chat_id = :chatId order by time desc")
    fun getAllMessages(chatId: String): Flow<List<MessageEntity>>

    @Upsert
    suspend fun upsertMessage(messageEntity: MessageEntity)

    @Upsert
    suspend fun upsertMessages(messageEntity: List<MessageEntity>)

    @Query(
        value = "delete from message where message_id in (:ids) "
    )
    suspend fun deleteMessages(ids: List<String>)
}