package com.chatappfrontend.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessagePayloadDTO(
    @SerialName("type")
    val type: String,

    @SerialName("senderId")
    val senderId: String,

    @SerialName("recipientId")
    val recipientId: String,

    @SerialName("content")
    val content: String,
)