package com.chatappfrontend.data.websocket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterWSConnectionDTO(
    val type: String,
    val userId: String,

)