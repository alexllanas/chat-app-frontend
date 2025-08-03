package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserListDTO(
    @SerialName("users")
    val users: List<UserDTO>
)
