package com.trodar.authentication.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.trodar.firebase.Authentication
import com.trodar.firebase.utils.Result
import com.trodar.firebase.utils.asResult
import com.trodar.ui.BaseViewModel
import com.trodar.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuthentication: Authentication,
    networkMonitor: NetworkMonitor,
) : BaseViewModel(networkMonitor) {
    private val _userUiState = MutableStateFlow(UserState())
    val userUiState = _userUiState.asStateFlow()

    fun loginWithEmail() {
        viewModelScope.launch {
            firebaseAuthentication.emailAuthentication(
                email = userUiState.value.email,
                password = userUiState.value.password,
            ).asResult().collect {
                when (it) {
                    is Result.Success -> {
                        authenticated()
                    }

                    is Result.Error -> {
                        loadingChange(false)
                        addError(it.exception.message ?: "Error")
                    }

                    Result.Loading -> loadingChange(true)
                }

            }
        }
    }

    fun loginTwitter(context: Context) {
        viewModelScope.launch {
            firebaseAuthentication.twitterAuthentication(context = context).asResult().collect {
                when (it) {
                    is Result.Success -> {
                        authenticated()
                    }

                    is Result.Error -> {
                        loadingChange(false)
                        addError(it.exception.message ?: "Error")
                    }

                    is Result.Loading -> {
                        loadingChange(true)
                    }
                }
            }
        }
    }

    fun loginFaceBook(token: String) {
        viewModelScope.launch {
            firebaseAuthentication.facebookAuthentication(token).asResult().collect {
                when (it) {
                    is Result.Success -> {
                        authenticated()
                    }

                    is Result.Error -> {
                        loadingChange(false)
                        addError(it.exception.message ?: "Error")
                    }

                    is Result.Loading -> {
                        loadingChange(true)
                    }
                }
            }
        }
    }

    fun loginGoogle(token: String) {
        viewModelScope.launch {
            firebaseAuthentication.googleAuthentication(token).asResult().collect {
                when (it) {
                    is Result.Success -> {
                        authenticated()
                    }

                    is Result.Error -> {
                        loadingChange(false)
                        addError(it.exception.message ?: "Error")
                    }

                    is Result.Loading -> {
                        loadingChange(true)
                    }
                }
            }
        }
    }

    fun onEmailChange(email: String) {
        _userUiState.update {
            it.copy(email = email)
        }
    }

    fun onPasswordChange(password: String) {
        _userUiState.update {
            it.copy(password = password)
        }
    }

    private fun loadingChange(value: Boolean) {
        _userUiState.update {
            it.copy(loading = value)
        }
    }

    private fun authenticated() {
        _userUiState.update {
            it.copy(loading = false, isAuthenticated = true)
        }
    }

}

data class UserState(
    val email: String = "",
    val password: String = "",
    val isAuthenticated: Boolean = false,
    val loading: Boolean = false
)


