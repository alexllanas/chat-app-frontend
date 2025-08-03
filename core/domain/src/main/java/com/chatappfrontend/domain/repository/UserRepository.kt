package com.chatappfrontend.domain.repository

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.User

interface UserRepository {

    suspend fun getUsers(): ResultWrapper<List<User>>
}