package com.chatappfrontend.domain.repository

import com.chatappfrontend.common.DataResource
import com.chatappfrontend.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers(currentUserId: String): Flow<DataResource<List<User>>>
}