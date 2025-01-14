package com.trodar.chat.chat

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType
import androidx.compose.foundation.content.ReceiveContentListener
import androidx.compose.foundation.content.TransferableContent
import androidx.compose.foundation.content.consume
import androidx.compose.foundation.content.contentReceiver
import androidx.compose.foundation.content.hasMediaType
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.outlined.AddReaction
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.core.content.ContextCompat
import com.trodar.ui.audio.ProgressBar
import com.trodar.ui.checkPermission
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ConstraintLayoutScope.ChatBox(
    state: TextFieldState,
    constraint: ConstrainedLayoutReference,
    onSendClick: () -> Unit,
    onImageSendClick: (Uri) -> Unit,
    onVoiceClick: (Boolean) -> Unit,
) {


    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isLongClick = remember { MutableStateFlow(false) }
    val viewConfiguration = LocalViewConfiguration.current
    val audioPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {

    }

    LaunchedEffect(interactionSource) {

        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isLongClick.value = false
                    delay(viewConfiguration.longPressTimeoutMillis)
                    val permission = Manifest.permission.RECORD_AUDIO

                    if (checkPermission(
                            permission,
                            context,
                            audioPermissionLauncher
                        )
                    ) {
                        isLongClick.value = true
                        onVoiceClick(true)
                    }

                    if (ContextCompat.checkSelfPermission(
                            context,
                            permission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        isLongClick.value = true
                        onVoiceClick(true)
                    } else {
                        audioPermissionLauncher.launch(permission)
                    }
                }

                is PressInteraction.Release -> {
                    isLongClick.value = false
                    onVoiceClick(false)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .constrainAs(constraint) {
                bottom.linkTo(parent.bottom)
            }
            .fillMaxWidth()
            .heightIn(min = 16.dp, max = 156.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            if (isLongClick.collectAsState().value) {
                VoiceControls()

            } else {
                TextControls(
                    state = state,
                    onImageSendClick = onImageSendClick,
                )
            }

            if (state.text.isEmpty()) {
                IconButton(
                    onClick = {},
                    interactionSource = interactionSource,
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardVoice,
                        contentDescription = "Microphone",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            } else {
                IconButton(
                    onClick = onSendClick,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}


@Composable
fun RowScope.VoiceControls() {
    Row(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "00:00.000",
            modifier = Modifier.widthIn(min = 12.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
        ProgressBar(songDuration = "0:00")
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.TextControls(
    state: TextFieldState,
    onImageSendClick: (Uri) -> Unit,
) {
    IconButton(
        onClick = {},
    ) {
        Icon(
            imageVector = Icons.Outlined.AddReaction,
            contentDescription = "Reactions",
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
    BasicTextField(
        state = state,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp)
            .padding(bottom = 4.dp)
            .contentReceiver(textFieldListener(onImageSendClick)),
        textStyle = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun textFieldListener(
    onImageSend: (Uri) -> Unit,
): ReceiveContentListener {
    return object : ReceiveContentListener {
        override fun onReceive(
            transferableContent: TransferableContent
        ): TransferableContent? {
            if (!transferableContent.hasMediaType(MediaType.Image)) {
                return transferableContent
            }
            return transferableContent
                .consume { item ->
                    item.uri?.let {
                        onImageSend(it)
                        true
                    } ?: false
                }
        }
    }
}


@Preview
@Composable
fun ChatBoxPreview() {
    ConstraintLayout {
        val box = createRef()
        ChatBox(
            state = TextFieldState(
                """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            """.trimIndent()
            ),
            constraint = box,
            onSendClick = {},
            onVoiceClick = {},
            onImageSendClick = {},
        )
    }
}
