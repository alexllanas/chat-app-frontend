package com.chatappfrontend.data.repository

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.User
import com.chatappfrontend.data.mapper.toUser
import com.chatappfrontend.domain.repository.UserRepository
import com.example.network.CAFNetworkDataSource
import com.example.network.utils.NetworkResponseParser
import com.example.network.utils.safeApiCall
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val network: CAFNetworkDataSource,
) : UserRepository {

    override suspend fun getUsers(): ResultWrapper<List<User>> {
        return safeApiCall(
            apiCall = { network.getUsers() },
            onSuccess = { body -> body.users.map { it.toUser() } },
        )
    }
}