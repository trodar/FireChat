package com.trodar.authentication.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trodar.ui.NetworkUiState
import com.trodar.ui.ShowErrorSnackBar
import com.trodar.ui.components.CustomCircularProgressBar

@Composable
fun LoginRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onAuthenticated: () -> Unit,
    onSignUpClick: () -> Unit,
){

    val networkState by loginViewModel.appBarSettings.collectAsStateWithLifecycle()
    val userUiState by loginViewModel.userUiState.collectAsState()

    if (userUiState.isAuthenticated) {
        onAuthenticated()
    }
    when (networkState) {
        NetworkUiState.Loading -> { }

        is NetworkUiState.Success -> {
            CustomCircularProgressBar(userUiState.loading)

            LoginScreen(
                isOffline = (networkState as NetworkUiState.Success).appBarSettings.isOffline,
                userUiState = userUiState,
                onEmailChange = loginViewModel::onEmailChange,
                onPasswordChange = loginViewModel::onPasswordChange,
                onEmailLoginClick = loginViewModel::loginWithEmail,
                onSignUpClick = onSignUpClick,
                onTwitterClick = loginViewModel::loginTwitter,
                onGoogleClick = loginViewModel::loginGoogle,
                onFaceBookClick = loginViewModel::loginFaceBook,
                onError = loginViewModel::addError
            )
        }
    }

    val errorState by loginViewModel.uiErrorState.collectAsState()
    ShowErrorSnackBar(
        errorState = errorState,
        snackbarHostState = snackbarHostState,
        onErrorDismiss = loginViewModel::removeError
    )
}