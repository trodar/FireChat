package com.trodar.firebase.database

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.trodar.firebase.FirebaseDataSource
import com.trodar.firebase.model.AccountChatNetwork
import com.trodar.firebase.model.AccountNetwork
import com.trodar.firebase.model.AccountWithLastMessageTuple
import com.trodar.firebase.model.MessageNetwork
import com.trodar.firebase.model.UpdateAccountChatData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Clock
import javax.inject.Inject

class FirebaseFetchData @Inject constructor(
) : BaseFireBase(), FirebaseDataSource {

    override fun getAccountData(myId: String, lastUpdateTime: Long): Flow<List<AccountWithLastMessageTuple>> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    database.child(ACCOUNT_CHAT_TABLE).child(myId).orderByChild("time")
                        .startAfter(lastUpdateTime.toDouble()).get().addOnSuccessListener { accountChatSnapShot ->

                            val accountChats = mutableListOf<AccountChatNetwork>()
                            accountChatSnapShot.children.forEach { accountChatItem ->
                                val accountChat = accountChatItem.getValue<AccountChatNetwork>() ?: return@forEach
                                accountChats.add(accountChat)
                            }

                        val accounts = mutableListOf<AccountNetwork>()
                        dataSnapshot.children.forEach { accountItem ->
                            val account = accountItem.getValue<AccountNetwork>() ?: return@forEach
                            accounts.add(account)
                        }
                            val accountLastMessage = accounts.map { account ->
                                val last =
                                    accountChats.firstOrNull { it.accountId == account.accountId }
                                AccountWithLastMessageTuple(
                                    accountId = account.accountId,
                                    name = account.name,
                                    logo = account.logo,
                                    lastOnline = account.lastOnline,
                                    lastAccountId = last?.lastAccountId,
                                    lastMessage = last?.lastMessage,
                                    time = last?.time
                                )
                            }
                        trySend(accountLastMessage)

                    }.addOnFailureListener {
                        Log.d("MAcK", it.toString())
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }

            database.child(ACCOUNT_TABLE).orderByChild("lastOnline")
                .startAfter(lastUpdateTime.toDouble()).addValueEventListener(listener)
            awaitClose {
                database.removeEventListener(listener)
                channel.close()
            }
        }

    override fun getAccountLastMessage(
        myId: String,
        lastUpdateTime: Long
    ): Flow<List<AccountChatNetwork>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val accounts = mutableListOf<AccountChatNetwork>()
                dataSnapshot.children.forEach { accountList ->
                    val account = accountList.getValue<AccountChatNetwork>() ?: return@forEach
                    accounts.add(account)
                }
                trySend(accounts)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.child(ACCOUNT_CHAT_TABLE).child(myId).orderByChild("time")
            .startAfter(lastUpdateTime.toDouble()).addValueEventListener(listener)
        awaitClose {
            database.removeEventListener(listener)
            channel.close()
        }
    }

    override suspend fun upsertAccount(account: AccountNetwork) {

        database.child(ACCOUNT_TABLE).child(account.accountId!!).setValue(account).await()
    }

    override suspend fun deleteAccount(account: AccountNetwork) {
        database.child(ACCOUNT_TABLE).child(account.accountId!!).removeValue().await()
    }

    override suspend fun updateAccountLastOnline(myId: String, time: Long) {
        database.child(ACCOUNT_TABLE).child(myId).child("lastOnline").setValue(time)
    }

    override fun getMessages(chatId: String, lastUpdateTime: Long): Flow<List<MessageNetwork>> =
        callbackFlow {

            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val messages = mutableListOf<MessageNetwork>()
                    dataSnapshot.children.forEach { chatList ->
                        val message = chatList.getValue<MessageNetwork>() ?: return@forEach
                        messages.add(message)
                    }
                    trySend(messages)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }

            database.child("$CHAT_TABLE/$chatId").orderByChild("time")
                .startAfter(lastUpdateTime.toDouble()).addValueEventListener(listener)
            awaitClose {
                database.removeEventListener(listener)
                channel.close()
            }

        }

    override suspend fun upsertMessage(updateAccountChatData: UpdateAccountChatData) {
        val firstAccountChat = AccountChatNetwork(
            accountId = updateAccountChatData.accountId,
            lastAccountId = updateAccountChatData.myId,
            lastMessage = updateAccountChatData.message,
            time = updateAccountChatData.time,
        )
        val secondAccountChat = AccountChatNetwork(
            accountId = updateAccountChatData.myId,
            lastAccountId = updateAccountChatData.myId,
            lastMessage = updateAccountChatData.message,
            time = updateAccountChatData.time,
        )
        val messageId = database.child(CHAT_TABLE).child(updateAccountChatData.chatId).push().key
        val message = MessageNetwork(
            messageId = messageId,
            authorId = updateAccountChatData.myId,
            content = updateAccountChatData.message,
            time = Clock.System.now().toEpochMilliseconds(),
            mediaType = updateAccountChatData.contentType,
            mediaUrl = updateAccountChatData.contentPath?.toString(),
            isDeleted = false,
        )
        val updateAccountChat = hashMapOf(
            "$ACCOUNT_CHAT_TABLE/${updateAccountChatData.myId}/${updateAccountChatData.chatId}" to firstAccountChat,
            "$ACCOUNT_CHAT_TABLE/${updateAccountChatData.accountId}/${updateAccountChatData.chatId}" to secondAccountChat,
            "$CHAT_TABLE/${updateAccountChatData.chatId}/$messageId" to message
        )
        database.updateChildren(updateAccountChat).await()
    }

    override suspend fun deleteMessage(message: MessageNetwork, chatId: String) {

    }

    companion object {
        const val ACCOUNT_TABLE = "accounts"
        const val ACCOUNT_CHAT_TABLE = "account_chats"
        const val CHAT_TABLE = "chats"
    }

}