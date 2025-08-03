package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatListDTO(

    @SerialName("chats")
    val chats: List<ChatDTO>
)