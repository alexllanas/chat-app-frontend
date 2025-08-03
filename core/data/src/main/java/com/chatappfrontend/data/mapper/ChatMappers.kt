package com.chatappfrontend.data.mapper

import com.chatappfrontend.domain.model.Chat
import com.example.network.model.ChatDTO

fun ChatDTO.toChat(): Chat {
    return Chat(
        id = id,
        userId = userId,
        username = username,
        lastMessage = lastMessage,
        lastMessageTimeStamp = lastMessageTimeStamp
    )
}