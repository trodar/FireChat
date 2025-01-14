package com.trodar.firechat.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun rememberFireChatState(
    navController: NavHostController = rememberNavController(),
)
: FireChatState{
    return remember {
        FireChatState(

            navController = navController,

        )
    }
}

@Stable
class FireChatState(
    val navController: NavHostController,

)