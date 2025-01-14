package com.trodar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trodar.utils.NetworkMonitor
import com.trodar.utils.model.BaseViewErrorState
import com.trodar.utils.model.ErrorMessage
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@ViewModelScoped
open class BaseViewModel @Inject constructor(networkMonitor: NetworkMonitor) : ViewModel() {

    private val viewErrorState = MutableStateFlow(
        BaseViewErrorState()
    )

    val uiErrorState = viewErrorState.asStateFlow()

    val appBarSettings: StateFlow<NetworkUiState> = networkMonitor.isOnline.map { isOnline ->

        NetworkUiState.Success(
            appBarSettings = NetworkSettings(isOffline = isOnline.not())
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
        initialValue = NetworkUiState.Loading
    )

    fun addError(message: String){
        viewModelScope.launch {
            viewErrorState.update {
                it.copy(
                    errorMessages = it.errorMessages + ErrorMessage(
                        id = UUID.randomUUID().mostSignificantBits,
                        message = message
                    )
                )
            }
        }
    }

    fun removeError(id: Long) {
        viewModelScope.launch {
            viewErrorState.update { currentUiState ->
                val errorMessages = currentUiState.errorMessages.filterNot { it.id == id }
                currentUiState.copy(errorMessages = errorMessages)
            }
        }
    }
}






