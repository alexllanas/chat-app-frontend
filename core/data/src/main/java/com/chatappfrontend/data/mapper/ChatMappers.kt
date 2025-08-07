package com.chatappfrontend.data.mapper

import com.chatappfrontend.domain.model.ChatInfo
import com.chatappfrontend.domain.model.ChatSession
import com.example.network.model.ChatInfoDTO
import com.example.network.model.ChatSessionDTO

fun ChatInfoDTO.toChatInfo(): ChatInfo {
    return ChatInfo(
        id = id,
        userId = userId,
        username = username,
        lastMessage = lastMessage,
        lastMessageTimeStamp = lastMessageTimeStamp
    )
}

fun ChatSessionDTO.toChatSession(): ChatSession {
    return ChatSession(
        chatId = chatId,
        userId = userId,
        username = username,
        messages = messages.map { it.toMessage() },
    )
}