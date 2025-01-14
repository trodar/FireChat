package com.trodar.firebase

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface Authentication {


    suspend fun emailAuthentication(
        email: String,
        password: String,
        userName: String? = null,
        isLogin: Boolean = true,
        photo: Uri? = null,
    ): Flow<Boolean>

    suspend fun facebookAuthentication(token: String): Flow<Boolean>
    suspend fun googleAuthentication(token: String): Flow<Boolean>
    suspend fun twitterAuthentication(context: Context): Flow<Boolean>
}


