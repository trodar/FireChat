package com.trodar.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trodar.firebase.model.AccountNetwork
import com.trodar.firebase.model.AccountWithLastMessageTuple
import com.trodar.model.Account

/**
 * contact table - list user's contacts
 */
@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey
    @ColumnInfo("account_id") val accountId: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("logo") val logo: String,
    @ColumnInfo("last_online") val lastOnline: Long,
    @ColumnInfo("not_read_count") val notReadCount: Int = 0,
    @ColumnInfo("last_account_id") val lastAccountId: String,
    @ColumnInfo("last_message") val lastMessage: String,
)

fun AccountNetwork.asAccountEntity() = AccountEntity(
    accountId = accountId ?: "",
    name = name ?: "",
    logo = logo ?: "",
    lastOnline = lastOnline ?: 0,
    notReadCount =  0,
    lastMessage = "",
    lastAccountId = ""
)

fun AccountWithLastMessageTuple.asAccountEntity() = AccountEntity(
    accountId = accountId ?: "",
    name = name ?: "",
    logo = logo ?: "",
    lastOnline = lastOnline ?: 0,
    notReadCount =  0,
    lastMessage = lastMessage ?: "",
    lastAccountId = lastAccountId ?: ""
)

fun AccountEntity.asAccountModel() = Account(
    accountId = accountId,
    name = name,
    logo = logo,
    lastOnline = lastOnline ,
    lastAccountId = lastAccountId,
    lastMessage = lastMessage,
    notReadCount = notReadCount,
)


