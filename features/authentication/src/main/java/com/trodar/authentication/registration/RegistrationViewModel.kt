package com.trodar.authentication.registration

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.lifecycle.viewModelScope
import com.trodar.firebase.Authentication
import com.trodar.firebase.utils.Result
import com.trodar.firebase.utils.asResult
import com.trodar.ui.BaseViewModel
import com.trodar.utils.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val firebaseAuthentication: Authentication,
    @ApplicationContext private val context: Context,
    networkMonitor: NetworkMonitor,
) : BaseViewModel(networkMonitor) {

    private val _userUiState = MutableStateFlow(UserState())
    val userUiState = _userUiState.asStateFlow()

    fun registerEmail() {

        viewModelScope.launch {
            firebaseAuthentication.emailAuthentication(
                email = userUiState.value.email,
                password = userUiState.value.password,
                userName = userUiState.value.userName,
                isLogin = false,
                photo = userUiState.value.logoUri ?: context.resourceUri( com.trodar.ui.R.drawable.core_ui_user)
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

    fun registerTwitter(context: Context) {

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

    fun registerFaceBook(token: String) {
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

    fun registerGoogle(token: String) {
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

    fun onLoginChange(login: String) {
        _userUiState.update {
            it.copy(userName = login)
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

    fun onLogoChange(uri: Uri) {
        _userUiState.update {
            it.copy(logoUri = uri)
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

    private fun Context.resourceUri(
        @DrawableRes resourceId: Int
    ): Uri = with(resources) {
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(getResourcePackageName(resourceId))
            .appendPath(getResourceTypeName(resourceId))
            .appendPath(getResourceEntryName(resourceId))
            .build()
    }
}

data class UserState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val logoUri: Uri? = null,
    val isAuthenticated: Boolean = false,
    val loading: Boolean = false
)
