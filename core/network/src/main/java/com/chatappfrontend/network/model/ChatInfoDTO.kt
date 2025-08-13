package com.chatappfrontend.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatInfoDTO(
    @SerialName("id")
    val id: String,

    @SerialName("userId")
    val userId: String,

    @SerialName("username")
    val username: String,

    @SerialName("lastMessage")
    val lastMessage: String,

    @SerialName("lastMessageTimeStamp")
    val lastMessageTimeStamp: String,
)
