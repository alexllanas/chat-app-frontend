package com.example.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProtectedResponse(
    val message: String,
    val id: String,
    val email: String,
)