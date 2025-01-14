package com.trodar.model


data class Chat(
    val messageId: String,
    val chatId: String,
    val authorId: String,
    val content: String,
    val time: Long,
    val mediaType: MediaType?,
    val mediaUrl: String?,
)

enum class MediaType {
    AUDIO, VIDEO, IMAGE
}

