package com.trodar.domain.account

import com.trodar.data.repository.SettingsRepository
import com.trodar.firebase.FirebaseDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AccountUpdateLastOnlineUseCase @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(time: Long) {
        val settings = settingsRepository.settingsData.firstOrNull()

        if (settings?.user?.id.isNullOrEmpty()) return

        firebaseDataSource.updateAccountLastOnline(settings!!.user.id, time)
    }
}