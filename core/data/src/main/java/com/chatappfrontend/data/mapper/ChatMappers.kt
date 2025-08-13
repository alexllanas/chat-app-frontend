package com.chatappfrontend.data.mapper

import com.chatappfrontend.domain.model.ChatInfo
import com.chatappfrontend.domain.model.ChatSession
import com.chatappfrontend.database.model.ChatInfoEntity
import com.chatappfrontend.network.model.ChatInfoDTO
import com.chatappfrontend.network.model.ChatSessionDTO

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