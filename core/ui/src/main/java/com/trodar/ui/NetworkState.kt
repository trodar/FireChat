package com.trodar.ui

data class NetworkSettings(
    val isOffline: Boolean
)

sealed interface NetworkUiState {
    data object Loading : NetworkUiState
    data class Success(val appBarSettings: NetworkSettings) : NetworkUiState
}
