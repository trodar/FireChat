package com.trodar.model

data class Account(
    val accountId: String,
    val name: String,
    val logo: String,
    val lastOnline: Long,
    val notReadCount: Int,
    val lastAccountId: String,
    val lastMessage: String,
)

