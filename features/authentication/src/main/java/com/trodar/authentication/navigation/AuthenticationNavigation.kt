package com.trodar.authentication.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.trodar.authentication.login.LoginRoute
import com.trodar.authentication.registration.RegistrationRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data object RegisterRoute


fun NavController.navigateToRegistration() = navigate(RegisterRoute)
fun NavController.navigateToLogin() = navigate(LoginRoute) { popUpTo(0)}

fun NavGraphBuilder.authRegisterScreen(
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onAuthenticated: () -> Unit,
) {
    composable<RegisterRoute> {
        RegistrationRoute(
            snackbarHostState = snackbarHostState,
            onBackClick = onBackClick,
            onAuthenticated = onAuthenticated,
        )
    }
}

fun NavGraphBuilder.authLoginScreen(
    snackbarHostState: SnackbarHostState,
    onSignUpClick: () -> Unit,
    onAuthenticated: () -> Unit,
) {
    composable<LoginRoute> {
        LoginRoute(
            snackbarHostState = snackbarHostState,
            onSignUpClick = onSignUpClick,
            onAuthenticated = onAuthenticated,
        )
    }
}
