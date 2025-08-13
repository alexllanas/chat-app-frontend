package com.chatappfrontend.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponseDTO(
    @SerialName("id")
    val id: String,

    @SerialName("username")
    val username: String,

    @SerialName("email")
    val email: String,

    @SerialName("accessToken")
    val accessToken: String,

    @SerialName("lastLogin")
    val lastLogin: String?,

    @SerialName("createdAt")
    val createdAt: String? = null
)
