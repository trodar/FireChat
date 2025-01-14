package com.trodar.domain.chat

import com.trodar.data.repository.MessageRepository
import com.trodar.firebase.FirebaseDataSource
import javax.inject.Inject

class SyncChatUseCase @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val messageRepository: MessageRepository,
) {

    suspend operator fun invoke(chatId: String, lastUpdate: Long?) {

        firebaseDataSource.getMessages(chatId, lastUpdate ?: 0).collect { chats ->
            if (chats.isNotEmpty())
              messageRepository.upsertMessages(
                  chat = chats,
                  chatId = chatId,
              )
        }
    }
}

