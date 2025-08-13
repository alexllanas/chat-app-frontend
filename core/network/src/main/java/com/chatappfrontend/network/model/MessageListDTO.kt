package com.chatappfrontend.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageListDTO(

    @SerialName("username")
    val username: String,

    @SerialName("messages")
    val messages: List<MessageDTO>
)