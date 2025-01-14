package com.trodar.chat.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.trodar.chat.accounts.ChatAccountRoute
import com.trodar.chat.chat.ChatRoute
import kotlinx.serialization.Serializable


@Serializable
data object ChatAccountRoute

@Serializable
data class ChatDetailRoute(val accountId: String)

fun NavController.navigateToChatAccount(navOptions: NavOptions? = null) =
    navigate(route = ChatAccountRoute, navOptions)

fun NavController.navigateToChatDetail(userId: String) =
    navigate(route = ChatDetailRoute(accountId = userId))


fun NavGraphBuilder.chatAccountScreen(
    onAccountClick: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    onSignOut: () -> Unit
) {
    composable<ChatAccountRoute> {
        ChatAccountRoute(
            onAccountClick = onAccountClick,
            snackbarHostState = snackbarHostState,
            onSignOut = onSignOut,
        )
    }
}

fun NavGraphBuilder.chatDetailScreen(
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
) {
    composable<ChatDetailRoute> { navBackStackEntry ->
        val chatDetailRoute: ChatDetailRoute = navBackStackEntry.toRoute()
        ChatRoute(
            snackbarHostState = snackbarHostState,
            accountId = chatDetailRoute.accountId,
            onBackClick = onBackClick,
        )
    }
}