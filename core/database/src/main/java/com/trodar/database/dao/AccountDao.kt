package com.trodar.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.trodar.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("""
        Select *
        from accounts 
        where account_id <> :accountId
        """
    )
    fun getAccountsWithLastData(accountId: String): Flow<List<AccountEntity>>

    @Query("Select * from accounts where account_id = :accountId")
    fun getAccount(accountId: String): Flow<AccountEntity>

    @Upsert
    suspend fun upsertAccount(accountEntity: AccountEntity)
    @Upsert
    suspend fun upsertAccounts(accountEntity: List<AccountEntity>)
    @Query("""UPDATE accounts 
        SET last_message =:message, last_account_id = :lastAccountId 
        WHERE account_id =:accountId
        """)
    suspend fun updateAccountLastMessage(accountId: String, lastAccountId: String, message: String)

    @Delete
    suspend fun deleteAccount(accountEntity: AccountEntity)

    @Query(
        """
            Delete from accounts
            where account_id in (:ids)
        """
    )
    suspend fun deleteAccounts(ids: List<String>)
}