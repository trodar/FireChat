package com.trodar.firechat.ui

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.trodar.firechat.navigation.FireChatNavHost

@Composable
fun FireChatApp(
    appState: FireChatState,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val modifier = Modifier.imePadding()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = { SnackbarHost(snackbarHostState, modifier) },
    ) { paddingValues ->
        Row(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Log.d("MAcK", paddingValues.toString())
            FireChatNavHost(
                appState = appState,
                snackbarHostState = snackbarHostState,
            )
        }
    }
}