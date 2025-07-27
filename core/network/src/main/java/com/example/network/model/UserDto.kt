package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,

    val username: String,

    val email: String,

    @SerialName("access_token")
    val accessToken: String,

    @SerialName("created_at")
    val createdAt: String? = null,
)
