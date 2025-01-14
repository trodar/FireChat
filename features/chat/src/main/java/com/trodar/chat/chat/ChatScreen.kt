package com.trodar.chat.chat

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.trodar.designtemplate.theme.FireChatTheme
import com.trodar.model.Account
import com.trodar.model.Chat
import com.trodar.ui.AppBarRoute
import com.trodar.ui.ChatListPreviewParameterProvider
import com.trodar.ui.DevicePreviews
import com.trodar.ui.components.ProfileImage
import kotlinx.coroutines.launch


@Composable
fun ChatScreen(
    myId: String,
    account: Account,
    chats: List<Chat>?,
    chatBox: TextFieldState,
    isOffline: Boolean,
    play: Play,
    onBackClick: () -> Unit,
    onLogoClick: () -> Unit,
    onSendClick: () -> Unit,
    onImageSendClick: (Uri) -> Unit,
    onVoiceClick: (Boolean) -> Unit,
    onPlayClick: (String, String) -> Unit,
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            .positionAwareImePadding()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (header, column, box) = createRefs()
        Header(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
            },
            isOffline = isOffline,
            account = account,
            onBackClick = onBackClick,
            onLogoClick = onLogoClick,
        )

        Box(
            modifier = Modifier.constrainAs(column) {
                top.linkTo(header.bottom)
                bottom.linkTo(box.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }
        ) {
            if (chats != null)
                Body(
                    chats = chats,
                    myId = myId,
                    scrollState = scrollState,
                    play = play,
                    onScrollEvent = {
                        scope.launch {
                            scrollState.animateScrollToItem(0)
                        }
                    },
                    onPlayClick = onPlayClick,
                )
        }
        ChatBox(
            state = chatBox,
            constraint = box,
            onSendClick = onSendClick,
            onVoiceClick = onVoiceClick,
            onImageSendClick = onImageSendClick,
        )
    }
}

fun Modifier.positionAwareImePadding() = composed {
    var consumePadding by remember { mutableIntStateOf(0) }
    onGloballyPositioned { coordinates ->
        val rootCoordinate = coordinates.findRootCoordinates()
        val bottom = coordinates.positionInWindow().y + coordinates.size.height

        consumePadding = (rootCoordinate.size.height - bottom).toInt()
    }
        .consumeWindowInsets(PaddingValues(bottom = (consumePadding / LocalDensity.current.density).dp))
        .imePadding()
}

@Composable
fun Header(
    modifier: Modifier,
    isOffline: Boolean,
    account: Account,
    onLogoClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    Box(modifier = modifier) {
        AppBarRoute(
            isOffline = false,
            onBackClick = onBackClick,
            title = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    ProfileImage(
                        imageSize = 40.dp,
                        uri = if (account.logo.isNotEmpty()) Uri.parse(account.logo)
                        else resourceToUri(
                            context,
                            com.trodar.ui.R.drawable.core_ui_user_circle
                        ),
                        onClick = onLogoClick,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = account.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = if (isOffline) "waiting internet connection" else "Online",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            },
            onSettingsActionClick = {},
            onCallClick = {},
        )
    }
}

@Composable
fun Body(
    chats: List<Chat>,
    myId: String,
    scrollState: LazyListState,
    play: Play,
    onScrollEvent: () -> Unit,
    onPlayClick: (String, String) -> Unit,
) {
    LazyColumn(
        reverseLayout = true,
        state = scrollState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = chats,
            key = { it.messageId },
            contentType = { "messageItem" }
        ) { chat ->
            MessageItem(
                chat = chat,
                myId = myId,
                play = play,
                onPlayClick = onPlayClick,
            )
        }
    }
    onScrollEvent()
}

fun resourceToUri(context: Context, resID: Int): Uri {
    return Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.resources.getResourcePackageName(resID) + '/' +
                context.resources.getResourceTypeName(resID) + '/' +
                context.resources.getResourceEntryName(resID)
    )
}


@Composable
@DevicePreviews
fun ChatScreenPreview(
    @PreviewParameter(ChatListPreviewParameterProvider::class)
    chats: List<Chat>
) {
    FireChatTheme {
        ChatScreen(
            myId = "1234423",
            chats = chats,
            isOffline = false,
            play = Play(),
            account = Account("", "Jon", "", 0, 0, "", "Hello"),
            chatBox = TextFieldState("I'm here"),
            onBackClick = {},
            onLogoClick = {},
            onVoiceClick = {},
            onSendClick = {},
            onImageSendClick = {},
            onPlayClick = { _, _ -> },
        )
    }
}