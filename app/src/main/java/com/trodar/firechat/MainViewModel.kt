package com.trodar.firechat

import androidx.lifecycle.viewModelScope
import com.trodar.data.repository.SettingsRepository
import com.trodar.domain.account.AccountUpdateLastOnlineUseCase
import com.trodar.model.DarkThemeConfig
import com.trodar.model.ThemeBrand
import com.trodar.model.SettingsData
import com.trodar.ui.BaseViewModel
import com.trodar.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val accountUpdateLastOnlineUseCase: AccountUpdateLastOnlineUseCase,
    networkMonitor: NetworkMonitor,
) : BaseViewModel(networkMonitor) {

    val uiState: StateFlow<MainActivityUiState> = settingsRepository.settingsData.map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

    fun updateDate() {
        viewModelScope.launch {
            settingsRepository.setLastUpdate(Clock.System.now().toEpochMilliseconds())
        }
    }

    fun updateLastOnline() {
        viewModelScope.launch {
            accountUpdateLastOnlineUseCase(Clock.System.now().toEpochMilliseconds())
        }
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val settingData: SettingsData) : MainActivityUiState {
        override val shouldDisableDynamicTheming = !settingData.useDynamicColor

        override val shouldUseAndroidTheme: Boolean = when (settingData.themeBrand) {
            ThemeBrand.DEFAULT -> false
            ThemeBrand.ANDROID -> true
        }

        override fun shouldUseDarkTheme(isSystemDarkTheme: Boolean) =
            when (settingData.darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> isSystemDarkTheme
                DarkThemeConfig.LIGHT -> false
                DarkThemeConfig.DARK -> true
            }
    }

    fun shouldKeepSplashScreen() = this is Loading

    val shouldDisableDynamicTheming: Boolean get() = true

    val shouldUseAndroidTheme: Boolean get() = false

    fun shouldUseDarkTheme(isSystemDarkTheme: Boolean) = isSystemDarkTheme
}