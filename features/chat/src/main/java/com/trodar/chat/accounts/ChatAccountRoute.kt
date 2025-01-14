package com.trodar.chat.accounts

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trodar.ui.ShowErrorSnackBar
import com.trodar.ui.checkPermission

@Composable
fun ChatAccountRoute(
    chatAccountViewModel: ChatAccountViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onAccountClick: (String) -> Unit,
    onSignOut: () -> Unit,
) {

    val context = LocalContext.current
    val accountUiState: AccountUiState by chatAccountViewModel.uiState.collectAsStateWithLifecycle()
    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {

    }
    SideEffect {
        checkPermission(
            Manifest.permission.POST_NOTIFICATIONS,
            context,
            permissionLauncher
        )
    }



    ChatAccountScreen(
        accountUiState = accountUiState,
        showSettingDialog = showSettingsDialog,
        onAccountClick = {
            chatAccountViewModel.appBarSettings.value
            onAccountClick(it)
        },
        onSignOut = onSignOut,
        onError = chatAccountViewModel::addError,
        onShowSettingDialog = { showSettingsDialog = true },
        onHideSettingDialog = { showSettingsDialog = false },
    )
    val errorState by chatAccountViewModel.uiErrorState.collectAsState()
    ShowErrorSnackBar(
        errorState = errorState,
        snackbarHostState = snackbarHostState,
        onErrorDismiss = chatAccountViewModel::removeError
    )
}