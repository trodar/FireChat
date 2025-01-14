package com.trodar.data.repository

import com.trodar.datastore.UserData
import com.trodar.model.DarkThemeConfig
import com.trodar.model.SettingsData
import com.trodar.model.ThemeBrand
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val settingsData: Flow<SettingsData>

    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setLastUpdate(updateTime: Long)

    suspend fun setUserData(userData: UserData)
}