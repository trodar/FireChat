package com.trodar.domain.account

import com.trodar.data.repository.AccountRepository
import com.trodar.data.repository.SettingsRepository
import com.trodar.database.model.asAccountEntity
import com.trodar.firebase.FirebaseDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SyncAccountUseCase @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val settingsRepository: SettingsRepository,
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke() {
        val settings = settingsRepository.settingsData.firstOrNull()

        if (settings?.user?.id.isNullOrEmpty()) return

        firebaseDataSource.getAccountData(settings!!.user.id,settings.lastUpdate).collect { account ->
            if (account.isEmpty()) return@collect

            accountRepository.upsertAccounts(account
                .filter { it.accountId != settings.user.id }
                .map { it.asAccountEntity() }
            )
        }
    }
}