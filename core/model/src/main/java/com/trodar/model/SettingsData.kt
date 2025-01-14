package com.trodar.model

data class SettingsData (
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val lastUpdate: Long,
    val user: User,
)