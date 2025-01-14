package com.trodar.domain.chat

import android.content.Context
import android.net.Uri
import com.trodar.firebase.FirebaseDataSource
import com.trodar.firebase.StorageDataSource
import com.trodar.firebase.model.UpdateAccountChatData
import com.trodar.model.MediaType
import com.trodar.utils.util.getFileMetaData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject

class AddMessageUseCase @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val firebaseStorage: StorageDataSource,
    @ApplicationContext private val context: Context,
) {

    suspend operator fun invoke(updateAccountChatData: UpdateAccountChatData) {

        when (updateAccountChatData.contentType) {

            MediaType.VIDEO -> {
                return
            }

            MediaType.AUDIO -> {

                val audioData = getFileMetaData(context, updateAccountChatData.contentPath ?: Uri.EMPTY)
                val remotePath =
                    String.format(
                        CHAT_MEDIA_PATH,
                        updateAccountChatData.chatId,
                        audioData?.displayName ?: throw Exception("file not exist")
                    )

                val storePath = firebaseStorage
                    .upsertPhoto(updateAccountChatData.contentPath!!, remotePath)
                    .first()
                firebaseDataSource.upsertMessage(updateAccountChatData.copy(contentPath = storePath))
            }

            MediaType.IMAGE -> {

                val imageData =
                    getFileMetaData(context, updateAccountChatData.contentPath!!) ?: return

                val remotePath =
                    String.format(
                        CHAT_IMAGE_PATH,
                        updateAccountChatData.chatId,
                        imageData.displayName
                    )

                val storePath =
                    firebaseStorage.upsertPhoto(updateAccountChatData.contentPath!!, remotePath)
                        .first()
                firebaseDataSource.upsertMessage(updateAccountChatData.copy(contentPath = storePath))

            }

            null -> {
                firebaseDataSource.upsertMessage(updateAccountChatData)
            }
        }
    }

    companion object {
        const val CHAT_IMAGE_PATH = "chats/%s/images/%s"
        const val CHAT_MEDIA_PATH = "chats/%s/media/%s"

    }
}