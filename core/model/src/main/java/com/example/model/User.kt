package com.example.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,

    val email: String,

    @SerialName("access_token")
    val accessToken: String,

    @SerialName("created_at")
    val createdAt: String,
)