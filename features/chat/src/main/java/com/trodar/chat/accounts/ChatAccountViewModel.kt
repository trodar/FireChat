package com.trodar.chat.accounts

import androidx.lifecycle.viewModelScope
import com.trodar.data.repository.SettingsRepository
import com.trodar.domain.account.GetAccountsUseCase
import com.trodar.domain.account.SyncAccountChatUseCase
import com.trodar.domain.account.SyncAccountUseCase
import com.trodar.model.Account
import com.trodar.ui.BaseViewModel
import com.trodar.ui.NetworkUiState
import com.trodar.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatAccountViewModel @Inject constructor(
    private val syncAccountUseCase: SyncAccountUseCase,
    private val syncAccountChatUseCase: SyncAccountChatUseCase,
    getAccountsUseCase: GetAccountsUseCase,
    settingsRepository: SettingsRepository,
    networkMonitor: NetworkMonitor,
) : BaseViewModel(networkMonitor) {


    val uiState: StateFlow<AccountUiState> =
        settingsRepository.settingsData.flatMapLatest { settings ->
            getAccountsUseCase(settings.user.id)
                .flatMapLatest<List<Account>, AccountUiState> { data ->
                    appBarSettings.map { isOfflineState ->
                        when (isOfflineState) {
                            NetworkUiState.Loading -> {}
                            is NetworkUiState.Success -> {
                                return@map AccountUiState.Success(data, isOfflineState.appBarSettings.isOffline)
                            }
                        }
                        AccountUiState.Success(data, false)
                    }
                }
                .catch {
                    emit(AccountUiState.Error(it))
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AccountUiState.Loading,
        )

    init {
        syncAccount()
    }

    private fun syncAccount() {
        viewModelScope.launch {
            syncAccountUseCase()
        }
        viewModelScope.launch {
            syncAccountChatUseCase()
        }
    }
}

sealed interface AccountUiState {
    data class Success(
        val accounts: List<Account>,
        val isOffline: Boolean,
        ) : AccountUiState
    data class Error(val exception: Throwable) : AccountUiState
    data object Loading : AccountUiState
}