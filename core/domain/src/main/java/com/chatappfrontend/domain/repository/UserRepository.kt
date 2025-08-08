package com.chatappfrontend.domain.repository

import com.chatappfrontend.common.Resource
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers(currentUserId: String): Flow<Resource<List<User>>>
}