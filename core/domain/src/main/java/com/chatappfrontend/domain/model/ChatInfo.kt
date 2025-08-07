package com.chatappfrontend.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ChatInfo(
    val id: String,
    val userId: String,
    val username: String,
    val lastMessage: String,
    val lastMessageTimeStamp: String,
): Parcelable