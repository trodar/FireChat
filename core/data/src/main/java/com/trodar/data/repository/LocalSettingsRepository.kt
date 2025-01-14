package com.trodar.data.repository

import com.trodar.datastore.UserData
import com.trodar.firechat.core.datastore.SettingPreferencesDataSource
import com.trodar.model.DarkThemeConfig
import com.trodar.model.SettingsData
import com.trodar.model.ThemeBrand
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class LocalSettingsRepository @Inject constructor(
    private val settingsPreferences: SettingPreferencesDataSource,

    ): SettingsRepository {
    override val settingsData: Flow<SettingsData> =
        settingsPreferences.settings

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        settingsPreferences.setThemeBrand(themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        settingsPreferences.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        settingsPreferences.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setLastUpdate(updateTime: Long) {
        val settings = settingsPreferences.settings.firstOrNull()
        if (settings?.user?.id.isNullOrEmpty()) return
        settingsPreferences.setLastUpdate(updateTime)
    }

    override suspend fun setUserData(userData: UserData) {
        settingsPreferences.setUserData(userData)
    }
}