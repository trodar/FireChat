package com.trodar.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trodar.firebase.model.MessageNetwork
import com.trodar.model.Chat
import com.trodar.model.MediaType


@Entity(
    tableName = "message",
)
data class MessageEntity(
    @PrimaryKey
    @ColumnInfo("message_id") val messageId: String,
    @ColumnInfo("chat_id") val chatId: String,
    @ColumnInfo("author_id") val authorId: String,
    @ColumnInfo("content") val content: String,
    @ColumnInfo("time") val time: Long,
    @ColumnInfo("media_type") val mediaType: MediaType?,
    @ColumnInfo("media_url") val mediaUrl: String?,

)

fun MessageNetwork.asMessageEntity(chatId: String) = MessageEntity(
    messageId = messageId ?: "",
    chatId = chatId,
    authorId = authorId ?: "",
    content = content ?: "",
    time = time ?: 0,
    mediaType = mediaType,
    mediaUrl = mediaUrl,
)

fun MessageEntity.asMessageModel() = Chat(
    messageId = messageId,
    chatId = chatId,
    authorId = authorId,
    content = content,
    time = time,
    mediaType = mediaType,
    mediaUrl = mediaUrl,
)

fun Chat.asMessageEntity() = MessageEntity(
    messageId = messageId,
    chatId = chatId,
    authorId = authorId,
    content = content,
    time = time,
    mediaType = mediaType,
    mediaUrl = mediaUrl,
)