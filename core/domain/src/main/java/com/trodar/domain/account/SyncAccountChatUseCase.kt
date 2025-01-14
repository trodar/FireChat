package com.trodar.domain.account

import com.trodar.data.repository.AccountRepository
import com.trodar.data.repository.SettingsRepository
import com.trodar.firebase.FirebaseDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SyncAccountChatUseCase @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val settingsRepository: SettingsRepository,
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke() {
        val settings = settingsRepository.settingsData.firstOrNull()

        if (settings?.user?.id.isNullOrEmpty()) return
        firebaseDataSource.getAccountLastMessage(settings!!.user.id, settings.lastUpdate )
            .collect { accountChat ->
                if (accountChat.isEmpty()) return@collect

                accountChat.forEach { item ->
                    if (item.accountId == null || item.lastMessage == null) return@forEach
                    accountRepository.updateAccountLastMessage(item.accountId!!, item.lastAccountId!!, item.lastMessage!!)
                }
            }
    }
}