package com.trodar.firechat.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.google.firebase.auth.FirebaseAuth
import com.trodar.authentication.navigation.LoginRoute
import com.trodar.authentication.navigation.authLoginScreen
import com.trodar.authentication.navigation.authRegisterScreen
import com.trodar.authentication.navigation.navigateToLogin
import com.trodar.authentication.navigation.navigateToRegistration
import com.trodar.chat.navigation.ChatAccountRoute
import com.trodar.chat.navigation.chatAccountScreen
import com.trodar.chat.navigation.chatDetailScreen
import com.trodar.chat.navigation.navigateToChatAccount
import com.trodar.chat.navigation.navigateToChatDetail
import com.trodar.firechat.ui.FireChatState

@Composable
fun FireChatNavHost(
    appState: FireChatState,
    snackbarHostState: SnackbarHostState,
) {

    val navController = appState.navController
    val instance = FirebaseAuth.getInstance()
    val user = instance.currentUser

    NavHost(
        navController = navController,
        startDestination = if (user == null) LoginRoute else ChatAccountRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        authLoginScreen(
            snackbarHostState = snackbarHostState,
            onSignUpClick = navController::navigateToRegistration,
            onAuthenticated = navController::navigateToChatAccount
        )

        authRegisterScreen(
            snackbarHostState = snackbarHostState,
            onBackClick = navController::popBackStack,
            onAuthenticated = navController::navigateToChatAccount
        )

        chatAccountScreen(
            onAccountClick = { navController.navigateToChatDetail(it) },
            snackbarHostState = snackbarHostState,
            onSignOut = {
                instance.signOut()
                navController.navigateToLogin()
            }
        )
        chatDetailScreen(
            snackbarHostState = snackbarHostState,
            onBackClick = navController::popBackStack,
        )
    }
}