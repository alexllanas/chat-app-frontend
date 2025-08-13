package com.chatappfrontend.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerialName("id")
    val id: String,

    @SerialName("username")
    val username: String,

    @SerialName("email")
    val email: String,

    @SerialName("chatId")
    val chatId: String?
)
