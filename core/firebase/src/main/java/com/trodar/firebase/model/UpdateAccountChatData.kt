package com.trodar.firebase.model

import android.net.Uri
import com.trodar.model.MediaType

data class UpdateAccountChatData(
    val myId: String,
    val accountId: String,
    val chatId: String,
    val message: String? = null,
    val time: Long? = null,
    val contentType: MediaType? = null,
    val contentPath: Uri? = null,
)
