package com.chatappfrontend.data.mapper

import com.chatappfrontend.common.formatMessageTime
import com.chatappfrontend.domain.model.Message
import com.example.database.model.MessageEntity
import com.example.network.model.MessageDTO

fun MessageDTO.toMessage() = Message(
    id = id,
    chatId = chatId,
    senderId = senderId,
    recipientId = recipientId,
    content = content,
    createdAt = createdAt,
    isRead = isRead
)

fun MessageDTO.toMessageEntity() = MessageEntity(
    id = id,
    chatId = chatId,
    senderId = senderId,
    recipientId = recipientId,
    content = content,
    createdAt = formatMessageTime(createdAt),
    isRead = isRead
)

fun Message.toMessageEntity() = MessageEntity(
    id = id,
    chatId = chatId,
    senderId = senderId,
    recipientId = recipientId,
    content = content,
    createdAt = createdAt,
    isRead = isRead
)

fun MessageEntity.toMessage() = Message(
    id = id,
    chatId = chatId,
    senderId = senderId,
    recipientId = recipientId,
    content = content,
    createdAt = createdAt,
    isRead = isRead
)