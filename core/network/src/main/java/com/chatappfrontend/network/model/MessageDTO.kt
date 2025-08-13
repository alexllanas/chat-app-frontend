package com.chatappfrontend.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDTO(
    @SerialName("id")
    val id: String,

    @SerialName("chatId")
    val chatId: String,

    @SerialName("senderId")
    val senderId: String,

    @SerialName("recipientId")
    val recipientId: String,

    @SerialName("content")
    val content: String,

    @SerialName("createdAt")
    val createdAt: String,

    @SerialName("isRead")
    val isRead: Boolean
)