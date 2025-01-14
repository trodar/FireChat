package com.trodar.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.trodar.model.Chat
import com.trodar.ui.ChatListPreviewParameterProvider.PreviewParameterData.chats
import kotlinx.datetime.Clock

class ChatListPreviewParameterProvider : PreviewParameterProvider<List<Chat>> {
    override val values: Sequence<List<Chat>>
        get() = sequenceOf(chats)

    object PreviewParameterData {
        val chats: List<Chat> = listOf()
//            Chat(
//                id = 1,
//                contactId = 1,
//                messageType = MessageType.ME,
//                content = "Hello",
//                url = "",
//                date = Clock.System.now(),
//                isRead = true,
//            ),
//            Chat(
//                id = 2,
//                contactId = 1,
//                messageType = MessageType.YOU,
//                content = "Hello",
//                url = "",
//                date = Clock.System.now(),
//                isRead = true,
//            ),
//            Chat(
//                id = 3,
//                contactId = 1,
//                messageType = MessageType.ME,
//                content = "how are you?",
//                url = "",
//                date = Clock.System.now(),
//                isRead = false,
//            ),
//            Chat(
//                id = 4,
//                contactId = 1,
//                messageType = MessageType.YOU,
//                content = "I'm fine. Thank you",
//                url = "",
//                date = Clock.System.now(),
//                isRead = false,
//            ),
//            Chat(
//                id = 5,
//                contactId = 1,
//                messageType = MessageType.ME,
//                content = """
//                    |I'm fine. Thank you.
//                    |Our company designs and produces prefabricated metal buildings, primarily with the use of MBS (mbsweb.com), a proprietary.
//                        """.trimMargin(),
//                url = "",
//                date = Clock.System.now(),
//                isRead = false,
//            ),
//            Chat(
//                id = 6,
//                contactId = 1,
//                messageType = MessageType.YOU,
//                content = """
//                    |String literals may contain template expressions – pieces of code that are evaluated and whose results are concatenated into a string
//                    |Our company designs and produces prefabricated metal buildings, primarily with the use of MBS (mbsweb.com), a proprietary.
//                        """.trimMargin(),
//                url = "",
//                date = Clock.System.now(),
//                isRead = false,
//            ),
//        )

    }
}