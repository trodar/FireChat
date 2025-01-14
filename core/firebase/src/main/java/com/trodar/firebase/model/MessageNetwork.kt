package com.trodar.firebase.model

import com.trodar.model.MediaType

data class MessageNetwork(
    val messageId: String? = null,
    val authorId: String? = null,
    val content: String? = null,
    val time: Long?  = null,
    val mediaType: MediaType? = null,
    val mediaUrl: String? = null,
    val isDeleted: Boolean? = null,
)
