package com.chatappfrontend.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequestDTO(
    @SerialName("username")
    val username: String,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String
)