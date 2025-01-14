package com.trodar.authentication.registration

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trodar.ui.NetworkUiState
import com.trodar.ui.ShowErrorSnackBar
import com.trodar.ui.components.CustomCircularProgressBar
import com.trodar.ui.components.SelectPhotoBottomSheet

@Composable
fun RegistrationRoute(
    registrationViewModel: RegistrationViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onAuthenticated: () -> Unit,
    onBackClick: (() -> Unit)? = null,
) {

    val networkState by registrationViewModel.appBarSettings.collectAsStateWithLifecycle()
    val userUiState by registrationViewModel.userUiState.collectAsState()


    if (userUiState.isAuthenticated) {
        onAuthenticated()
    }
    var isClicked by remember {
        mutableStateOf(false)
    }

    if (isClicked) {
        SelectPhotoBottomSheet(
            onDismiss = { isClicked = false },
            onSuccess = {
                isClicked = false
                registrationViewModel.onLogoChange(it)

            },
        )
    }

    when (networkState) {
        NetworkUiState.Loading -> {}
        is NetworkUiState.Success -> {
            CustomCircularProgressBar(userUiState.loading)

            RegistrationScreen(
                userUiState = userUiState,
                onPhotoChange = { isClicked = true },
                onLoginChange = registrationViewModel::onLoginChange,
                onPasswordChange = registrationViewModel::onPasswordChange,
                onEmailChange = registrationViewModel::onEmailChange,
                isOffline = (networkState as NetworkUiState.Success).appBarSettings.isOffline,
                onEmailRegisterClick = registrationViewModel::registerEmail,
                onGoogleRegisterClick = registrationViewModel::registerGoogle,
                onFaceBookRegisterClick = registrationViewModel::registerFaceBook,
                onTwitterRegisterClick = registrationViewModel::registerTwitter,
                onError = registrationViewModel::addError,
                onBackClick = onBackClick,
            )
        }
    }

    val errorState by registrationViewModel.uiErrorState.collectAsState()
    ShowErrorSnackBar(
        errorState = errorState,
        snackbarHostState = snackbarHostState,
        onErrorDismiss = registrationViewModel::removeError
    )
}