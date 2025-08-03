package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageListDTO(

    @SerialName("messages")
    val messages: List<MessageDTO>
)