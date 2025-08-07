package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatSessionDTO(
    @SerialName("chatId")
    val chatId: String,

    @SerialName("userId")
    val userId: String,

    @SerialName("username")
    val username: String,

    @SerialName("messages")
    val messages: List<MessageDTO>
)
