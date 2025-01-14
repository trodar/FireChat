package com.trodar.chat.chat

import android.net.Uri
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.viewModelScope
import com.trodar.data.repository.SettingsRepository
import com.trodar.domain.account.GetAccountUseCase
import com.trodar.domain.chat.AddMessageUseCase
import com.trodar.domain.chat.GetMessageListUseCase
import com.trodar.domain.chat.SyncChatUseCase
import com.trodar.firebase.model.UpdateAccountChatData
import com.trodar.media.AudioRecorder
import com.trodar.model.Account
import com.trodar.model.Chat
import com.trodar.model.MediaType
import com.trodar.model.User
import com.trodar.ui.BaseViewModel
import com.trodar.ui.NetworkUiState
import com.trodar.utils.NetworkMonitor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.io.File

@HiltViewModel(assistedFactory = ChatViewModel.Factory::class)
class ChatViewModel @AssistedInject constructor(
    @Assisted private val accountId: String,
    private val addMessageUseCase: AddMessageUseCase,
    private val audioRecorder: AudioRecorder,
    getAccountUseCase: GetAccountUseCase,
    getMessageListUseCase: GetMessageListUseCase,
    syncChatUseCase: SyncChatUseCase,
    settingsRepository: SettingsRepository,
    networkMonitor: NetworkMonitor,
) : BaseViewModel(networkMonitor) {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    val chatUiState: StateFlow<ChatUiState> =
        settingsRepository.settingsData.flatMapLatest { settings ->
            val chatId = chatId(settings.user.id)
            getAccountUseCase(accountId).flatMapLatest { account ->
                updateAccount(account)

                getMessageListUseCase.getMessages(chatId).flatMapLatest { chats ->
                    updateChatAndMyId(
                        myId = settings.user.id,
                        chatId = chatId
                    )
                    appBarSettings.map { isOfflineState ->
                        when (isOfflineState) {
                            NetworkUiState.Loading -> {}
                            is NetworkUiState.Success -> {
                                return@map ChatUiState.Success(
                                    chats = chats,
                                    user = settings.user,
                                    accountId = accountId,
                                    isOffline = isOfflineState.appBarSettings.isOffline
                                )
                            }
                        }
                        ChatUiState.Success(
                            chats = chats,
                            user = settings.user,
                            accountId = accountId,
                            isOffline = false
                        )
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ChatUiState.Loading
        )

    init {
        viewModelScope.launch {
            uiState.collectLatest { value ->
                if (value.myId.isNotEmpty())
                    syncChatUseCase(
                        chatId = chatId(value.myId),
                        lastUpdate = 0,
                    )
            }
        }
        viewModelScope.launch {
            appBarSettings.collectLatest { value ->
                when (value) {
                    is NetworkUiState.Success -> {
                        updateOffline(value.appBarSettings.isOffline)
                    }

                    is NetworkUiState.Loading -> {

                    }
                }
            }
        }
    }

    fun sendMessage() {
        val content = uiState.value.textField.text
        valueChange(TextFieldState(""))
        viewModelScope.launch {
            val updateAccountChatData = UpdateAccountChatData(
                myId = uiState.value.myId,
                accountId = uiState.value.account!!.accountId,
                chatId = uiState.value.chatId,
                message = content.toString(),
                time = Clock.System.now().toEpochMilliseconds()
            )
            addMessageUseCase(updateAccountChatData)
        }
    }

    fun sendImage(uri: Uri) {
        viewModelScope.launch {

            val updateAccountChatData = UpdateAccountChatData(
                myId = uiState.value.myId,
                accountId = uiState.value.account!!.accountId,
                chatId = uiState.value.chatId,
                message = "",
                time = Clock.System.now().toEpochMilliseconds(),
                contentType = MediaType.IMAGE,
                contentPath = uri
            )
            addMessageUseCase(updateAccountChatData)
        }
    }


    fun startAudioRecording(path: File) {
        viewModelScope.launch {
            val fileName = File(path, getFileName())
            audioRecorder.startRecord(fileName)
        }
    }

    fun stopRecording() {
        viewModelScope.launch {
            audioRecorder.stopRecord()
            if (audioRecorder.getFile() == null) return@launch

            val updateAccountChatData = UpdateAccountChatData(
                myId = uiState.value.myId,
                accountId = uiState.value.account!!.accountId,
                chatId = uiState.value.chatId,
                message = "",
                time = Clock.System.now().toEpochMilliseconds(),
                contentType = MediaType.AUDIO,
                contentPath = Uri.parse(audioRecorder.getFile()?.toURI().toString())
            )

            addMessageUseCase(updateAccountChatData)
        }
    }

    fun play(path: String, messageId: String) {
        viewModelScope.launch {
            reversePlay(messageId)
            if (uiState.value.play.isPlaying) {
                audioRecorder.startPlay(path).collectLatest { finished ->
                    if (finished) {
                        reversePlay(messageId)
                    }
                }
            } else {
                audioRecorder.stopPlay()
            }
        }
    }

    private fun getFileName(): String {
        val t = Clock.System.now()
        val timeFileMinute = t.toEpochMilliseconds()
        return "Audio-$timeFileMinute.3gp"
    }

    private fun valueChange(text: TextFieldState) = _uiState.update { it.copy(textField = text) }

    private fun chatId(myId: String): String = if (myId > accountId) {
        accountId + myId
    } else {
        myId + accountId
    }

    private fun updateChatAndMyId(myId: String, chatId: String) {
        _uiState.update {
            it.copy(
                myId = myId,
                chatId = chatId
            )
        }
    }

    private fun reversePlay(chatId: String) {
        _uiState.update {
            it.copy(
                play = Play(
                    isPlaying = !it.play.isPlaying,
                    messageId = if (it.play.isPlaying) null else chatId
                )
            )
        }
    }

    private fun updateAccount(account: Account) {
        _uiState.update { it.copy(account = account) }
    }

    private fun updateOffline(value: Boolean) {
        _uiState.update { it.copy(isOffline = value) }
    }


    @AssistedFactory
    interface Factory {
        fun create(accountId: String): ChatViewModel
    }
}

data class UiState(
    val textField: TextFieldState = TextFieldState(""),
    val account: Account? = null,
    val myId: String = "",
    val chatId: String = "",
    val isOffline: Boolean = false,
    val play: Play = Play(),
)

data class Play(
    val isPlaying: Boolean = false,
    val messageId: String? = null,
)

sealed interface ChatUiState {

    data object Loading : ChatUiState

    data class LoadFailed(val exception: Exception) : ChatUiState

    data class Success(
        val chats: List<Chat>?,
        val user: User,
        val accountId: String,
        val isOffline: Boolean,
    ) : ChatUiState
}