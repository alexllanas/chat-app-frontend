package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val hash: String,
    val createdAt: String? = null,
)