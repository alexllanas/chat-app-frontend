package com.chatappfrontend.data.mapper

import com.chatappfrontend.domain.model.ChatInfo
import com.chatappfrontend.domain.model.ChatSession
import com.example.database.model.ChatInfoEntity
import com.example.network.model.ChatInfoDTO
import com.example.network.model.ChatSessionDTO

fun ChatInfoDTO.toChatInfo() = ChatInfo(
    id = id,
    userId = userId,
    username = username,
    lastMessage = lastMessage,
    lastMessageTimeStamp = lastMessageTimeStamp
)

fun ChatSessionDTO.toChatSession() = ChatSession(
    chatId = chatId,
    userId = userId,
    username = username,
    messages = messages.map { it.toMessage() },
)

fun ChatInfoDTO.toChatInfoEntity() = ChatInfoEntity(
    id = id,
    userId = userId,
    username = username,
    lastMessage = lastMessage,
    lastMessageTimeStamp = lastMessageTimeStamp
)

fun ChatInfoEntity.toChatInfo() = ChatInfo(
    id = id,
    userId = userId,
    username = username,
    lastMessage = lastMessage,
    lastMessageTimeStamp = lastMessageTimeStamp
)