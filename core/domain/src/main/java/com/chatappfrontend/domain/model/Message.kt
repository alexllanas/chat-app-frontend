package com.chatappfrontend.domain.model

data class Message(
    val id: String,
    val senderId: String,
    val recipientId: String,
    val content: String,
    val createdAt: String,
    val isRead: Boolean
)