package com.trodar.firebase.model

data class AccountWithLastMessageTuple(
    val accountId: String? = null,
    val name: String? = null,
    val logo: String? = null,
    val lastOnline: Long? = null,
    val lastAccountId: String? = null,
    val lastMessage: String? = null,
    val time: Long? = null,
)
