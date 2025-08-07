package com.chatappfrontend.domain

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.Message
import com.chatappfrontend.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    val incomingMessages: SharedFlow<Message> = messageRepository.incomingMessages

    suspend operator fun invoke(
        recipientId: String,
        content: String
    ) : ResultWrapper<Message.Status> {
        return messageRepository.sendMessage(
            recipientId = recipientId,
            content = content
        )
    }
}