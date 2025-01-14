package com.trodar.settings

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.trodar.data.repository.SettingsRepository
import com.trodar.domain.settings.UpdateLogoUseCase
import com.trodar.firebase.utils.Result
import com.trodar.firebase.utils.asResult
import com.trodar.model.DarkThemeConfig
import com.trodar.model.SettingsData
import com.trodar.model.ThemeBrand
import com.trodar.model.User
import com.trodar.ui.BaseViewModel
import com.trodar.ui.extension.toStoreUser
import com.trodar.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val updateLogoUseCase: UpdateLogoUseCase,
    private val settingsRepository: SettingsRepository,
    networkMonitor: NetworkMonitor,
) : BaseViewModel(networkMonitor) {

    private var scanResult: Flow<String> = flowOf("")

    val settingsUiState: StateFlow<SettingsUiState> =
        combine(
            scanResult,
            settingsRepository.settingsData
        ) { scanResult, settingData ->
            SettingsUiState.Success(
                settings = SettingData(
                    scanResult = scanResult,
                    settings = settingData
                )
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = SettingsUiState.Loading,
            )

    fun scanResultSaved(value: String) {
        viewModelScope.launch {
            scanResult = flowOf(value)
        }
    }

    fun updateThemeBrand(themeBrand: ThemeBrand) {
        viewModelScope.launch {
            settingsRepository.setThemeBrand(themeBrand)
        }
    }

    fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            settingsRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun updateDynamicColorPreference(useDynamicColor: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDynamicColorPreference(useDynamicColor)
        }
    }

    fun onLogoChange(uri: Uri, user: User) {
        viewModelScope.launch {
            updateLogoUseCase(uri)
                .asResult().collect {
                    when (it) {
                        is Result.Success -> {
                            settingsRepository.setUserData(user.toStoreUser(it.data.toString()))
                        }
                        is Result.Error -> {
                            addError(it.exception.message ?: "Logo not updated")
                        }

                        Result.Loading -> {}
                    }
                }
        }
    }
}

data class SettingData(
    val scanResult: String? = null,
    val settings: SettingsData? = null,
)

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val settings: SettingData) : SettingsUiState
}