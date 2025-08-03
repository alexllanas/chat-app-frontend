package com.chatappfrontend.data.mapper

import com.chatappfrontend.domain.model.Message
import com.example.network.model.MessageDTO

fun MessageDTO.toMessage(): Message {
    return Message(
        id = id,
        senderId = senderId,
        recipientId = recipientId,
        content = content,
        createdAt = createdAt,
        isRead = isRead
    )
}