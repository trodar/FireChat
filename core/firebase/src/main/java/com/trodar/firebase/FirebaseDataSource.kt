package com.trodar.firebase

import com.trodar.firebase.model.AccountChatNetwork
import com.trodar.firebase.model.AccountNetwork
import com.trodar.firebase.model.AccountWithLastMessageTuple
import com.trodar.firebase.model.MessageNetwork
import com.trodar.firebase.model.UpdateAccountChatData
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {

    fun getAccountData(myId: String, lastUpdateTime: Long): Flow<List<AccountWithLastMessageTuple>>

    fun getAccountLastMessage(myId: String, lastUpdateTime: Long): Flow<List<AccountChatNetwork>>

    suspend fun upsertAccount(account: AccountNetwork)
    suspend fun deleteAccount(account: AccountNetwork)
    suspend fun updateAccountLastOnline(myId: String, time: Long)

    fun getMessages(chatId: String, lastUpdateTime: Long): Flow<List<MessageNetwork>>

    suspend fun upsertMessage(updateAccountChatData: UpdateAccountChatData)
    suspend fun deleteMessage(message: MessageNetwork, chatId: String)
}