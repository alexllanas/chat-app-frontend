package com.chatappfrontend.data.repository

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.User
import com.chatappfrontend.data.mapper.toUser
import com.chatappfrontend.domain.repository.UserRepository
import com.example.network.RemoteDataSource
import com.example.network.utils.safeApiCall
import com.example.security.DataStoreManager
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val network: RemoteDataSource,
    private val dataStoreManager: DataStoreManager
) : UserRepository {

    override suspend fun getUsers(): ResultWrapper<List<User>> {
        val id = dataStoreManager.getUserId()?: ""
        return safeApiCall(
            apiCall = {
                network.getUsers(id)
            },
            onSuccess = { body -> body.users.map { it.toUser() } },
        )
    }
}