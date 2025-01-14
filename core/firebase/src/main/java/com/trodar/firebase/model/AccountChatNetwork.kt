package com.trodar.firebase.model

/**
 there is information about chat
 */
data class AccountChatNetwork(
    val accountId: String? = null,
    val lastAccountId: String? = null,
    val lastMessage: String? = null,
    val time: Long? = null,
)
