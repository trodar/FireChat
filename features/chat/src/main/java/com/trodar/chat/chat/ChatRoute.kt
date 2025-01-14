package com.trodar.chat.chat

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trodar.ui.ShowErrorSnackBar
import com.trodar.ui.components.ShimmerGridAnimation

@Composable
fun ChatRoute(
    accountId: String,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    chatViewModel: ChatViewModel = hiltViewModel<ChatViewModel, ChatViewModel.Factory>(
        creationCallback = { factory -> factory.create(accountId = accountId) }
    )

) {

    val context = LocalContext.current
    val chatUiState = chatViewModel.chatUiState.collectAsStateWithLifecycle()
    val uiState = chatViewModel.uiState.collectAsStateWithLifecycle()


    when (chatUiState.value) {
        is ChatUiState.LoadFailed -> {
            val errorMessage =
                (chatUiState.value as ChatUiState.LoadFailed).exception.message ?: "Error"
            chatViewModel.addError(errorMessage)
        }

        ChatUiState.Loading -> {
            ShimmerGridAnimation()
        }

        is ChatUiState.Success -> {
            ChatScreen(
                isOffline = uiState.value.isOffline,
                myId = uiState.value.myId,
                account = uiState.value.account!!,
                chats = (chatUiState.value as ChatUiState.Success).chats,
                chatBox = uiState.value.textField,
                play = uiState.value.play,
                onBackClick = onBackClick,
                onLogoClick = { },
                onVoiceClick = {
                    if (uiState.value.isOffline) {
                        chatViewModel.addError(context.getString(com.trodar.ui.R.string.core_ui_no_internet))
                    } else {
                        when (it) {
                            true -> {
                                val cacheDir = context.cacheDir
                                chatViewModel.startAudioRecording(cacheDir)
                            }

                            false -> {
                                chatViewModel.stopRecording()
                            }
                        }
                    }
                },
                onSendClick = {
                    if (uiState.value.isOffline) {
                        chatViewModel.addError(context.getString(com.trodar.ui.R.string.core_ui_no_internet))
                    } else {
                        chatViewModel.sendMessage()
                    }
                },
                onImageSendClick = {
                    if (uiState.value.isOffline) {
                        chatViewModel.addError(context.getString(com.trodar.ui.R.string.core_ui_no_internet))
                    } else {
                        chatViewModel.sendImage(it)
                    }
                },
                onPlayClick = chatViewModel::play,
            )
        }
    }
    val errorState by chatViewModel.uiErrorState.collectAsState()
    ShowErrorSnackBar(
        errorState = errorState,
        snackbarHostState = snackbarHostState,
        onErrorDismiss = chatViewModel::removeError
    )
}


