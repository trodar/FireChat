package com.trodar.chat.chat

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Pause
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.request.crossfade
import com.trodar.chat.R
import com.trodar.model.Chat
import com.trodar.model.MediaType
import com.trodar.ui.extension.toFormatString

@Composable
fun MessageItem(
    chat: Chat,
    myId: String,
    play: Play,
    onPlayClick: (String, String) -> Unit,
) {
    val isFromMe = chat.authorId == myId
    val context = LocalContext.current

    val imageLoader =
        ImageLoader.Builder(context)
            .components {
                add(AnimatedImageDecoder.Factory())
            }
            .crossfade(true)
            .build()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .padding(
                start = if (isFromMe) 32.dp else 0.dp,
                end = if (isFromMe) 0.dp else 32.dp,
            )
    ) {
        Box(
            modifier = Modifier
                .align(if (isFromMe) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (isFromMe) 48f else 0f,
                        bottomEnd = if (isFromMe) 0f else 48f,
                    )
                )
                .background(MaterialTheme.colorScheme.surface)
                .padding(top = 16.dp, start = 16.dp, bottom = 0.dp, end = 8.dp)
        ) {
            Column(
                modifier = Modifier.offset(y = (-12).dp),
                horizontalAlignment = if (isFromMe) Alignment.End else Alignment.Start
            ) {
                Text(
                    text = chat.time.toFormatString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                when(chat.mediaType ) {
                    MediaType.AUDIO -> {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {onPlayClick(chat.mediaUrl ?: "", chat.messageId) } ,
                                modifier = Modifier.size(48.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor =     MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            ) {
                                Icon(
                                    modifier = Modifier.size(32.dp),
                                    imageVector = if (play.isPlaying && play.messageId == chat.messageId) Icons.Sharp.Pause
                                    else Icons.Sharp.PlayArrow,
                                    contentDescription = "play icon",
                                    tint = MaterialTheme.colorScheme.primaryContainer
                                )
                            }
                            Icon(
                                painter = painterResource(R.drawable.features_chat_audio),
                                contentDescription = "audio image",
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }

                    }
                    MediaType.VIDEO -> {}
                    MediaType.IMAGE -> {
                        AsyncImage(
                            model = Uri.parse(chat.mediaUrl),
                            contentDescription = "Selected image",
                            imageLoader = imageLoader,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp)),
                            contentScale = ContentScale.Crop,
                            onError = {
                                Log.d("MAcK", it.result.throwable.message.toString())
                            }
                        )
                    }
                    null -> {
                        SelectionContainer {
                            Text(
                                text = chat.content,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            }
        }
    }
}