package com.trodar.firechat.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.trodar.datastore.DarkThemeConfigProto
import com.trodar.datastore.SettingsPreferences
import com.trodar.datastore.ThemeBrandProto
import com.trodar.datastore.UserData
import com.trodar.datastore.copy
import com.trodar.model.DarkThemeConfig
import com.trodar.model.SettingsData
import com.trodar.model.ThemeBrand
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingPreferencesDataSource @Inject constructor(
    private val settingsPreferences: DataStore<SettingsPreferences>,
) {
    val settings = settingsPreferences.data.map {

        SettingsData(
            themeBrand = when (it.themeBrand) {
                null,
                ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                ThemeBrandProto.UNRECOGNIZED,
                ThemeBrandProto.THEME_BRAND_DEFAULT,
                    -> ThemeBrand.DEFAULT

                ThemeBrandProto.THEME_BRAND_ANDROID -> ThemeBrand.ANDROID
            },
            darkThemeConfig = when (it.darkThemeConfig) {
                null,
                DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                DarkThemeConfigProto.UNRECOGNIZED,
                DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                    -> DarkThemeConfig.FOLLOW_SYSTEM

                DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT -> DarkThemeConfig.LIGHT
                DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
            },
            useDynamicColor = it.useDynamicColor,
            lastUpdate = it.lastUpdate,
            user = it.userData.toUser(),
        )
    }

    suspend fun setLastUpdate(updateTime: Long) {
        settingsPreferences.updateData { it.copy { this.lastUpdate = updateTime } }
        Log.i("MAcK", "DATE  UPDATED")
    }

    suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        settingsPreferences.updateData {
            it.copy {
                this.themeBrand = when (themeBrand) {
                    ThemeBrand.DEFAULT -> ThemeBrandProto.THEME_BRAND_DEFAULT
                    ThemeBrand.ANDROID -> ThemeBrandProto.THEME_BRAND_ANDROID
                }
            }
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        settingsPreferences.updateData {
            it.copy { this.useDynamicColor = useDynamicColor }
        }
    }

    suspend fun setUserData(userData: UserData?) {
        settingsPreferences.updateData {
            it.copy {
                this.userData =
                    userData ?: UserData.newBuilder().setId("").setLogo("").setName("").setEmail("")
                        .setStatus("").setPhone("").build()
            }
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        settingsPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM

                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }
}
