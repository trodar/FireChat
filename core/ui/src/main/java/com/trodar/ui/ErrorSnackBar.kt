package com.trodar.ui

import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.trodar.utils.model.BaseViewErrorState

@Composable
fun ShowErrorSnackBar(
    errorState: BaseViewErrorState,
    snackbarHostState: SnackbarHostState,
    onErrorDismiss: (Long) -> Unit
) {
    if (errorState.errorMessages.isEmpty()) return

    val errorMessage = remember(errorState) { errorState.errorMessages[0] }
    val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

    LaunchedEffect(errorMessage.id) {
        snackbarHostState.showSnackbar(
            message = errorMessage.message,
            duration = Short,
        )

        onErrorDismissState(errorMessage.id)
    }
}