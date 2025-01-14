package com.trodar.data.repository

import com.trodar.database.dao.AccountDao
import com.trodar.database.model.AccountEntity
import com.trodar.database.model.asAccountEntity
import com.trodar.database.model.asAccountModel
import com.trodar.firebase.model.AccountNetwork
import com.trodar.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalAccountRepository @Inject constructor(
    private val accountDao: AccountDao,

) : AccountRepository {
    override fun getAccount(accountId: String): Flow<Account> =
        accountDao.getAccount(accountId).map (AccountEntity::asAccountModel)


    override fun getAccountsWithLastData(accountId: String): Flow<List<Account>> =
        accountDao.getAccountsWithLastData(accountId)
            .map { it.map(AccountEntity::asAccountModel) }


    override suspend fun upsertAccount(account: AccountNetwork) =
        accountDao.upsertAccount(account.asAccountEntity())

    override suspend fun upsertAccounts(accountList: List<AccountEntity>) {
        accountDao.upsertAccounts(accountList)
    }

    override suspend fun updateAccountLastMessage(
        accountId: String,
        lastAccountId: String,
        message: String
    ) {
        accountDao.updateAccountLastMessage(accountId, lastAccountId, message)
    }

    override suspend fun deleteAccount(account: AccountNetwork) =
        accountDao.deleteAccount(account.asAccountEntity())

}