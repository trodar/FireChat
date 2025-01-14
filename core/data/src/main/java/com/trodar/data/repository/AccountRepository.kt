package com.trodar.data.repository

import com.trodar.database.model.AccountEntity
import com.trodar.firebase.model.AccountNetwork
import com.trodar.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun getAccount(accountId: String): Flow<Account>

    fun getAccountsWithLastData(accountId: String): Flow<List<Account>>

    suspend fun upsertAccount(account: AccountNetwork)

    suspend fun upsertAccounts(accountList: List<AccountEntity>)
    suspend fun updateAccountLastMessage(accountId: String, lastAccountId: String, message: String)

    suspend fun deleteAccount(account: AccountNetwork)
}