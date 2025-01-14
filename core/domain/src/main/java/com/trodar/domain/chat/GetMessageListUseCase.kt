package com.trodar.domain.chat

import com.trodar.data.repository.MessageRepository
import com.trodar.model.Chat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessageListUseCase  @Inject constructor(
    private val messageRepository: MessageRepository,

    ) {
    fun getMessages(chatId: String): Flow<List<Chat>> {
        return messageRepository.getMessages(chatId)
    }
}