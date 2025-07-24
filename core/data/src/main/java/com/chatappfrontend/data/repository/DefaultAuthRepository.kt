package com.chatappfrontend.data.repository

import com.example.model.User
import com.example.network.CAFNetworkDataSource
import javax.inject.Inject

internal class DefaultAuthRepository @Inject constructor(
    private val network: CAFNetworkDataSource
) : AuthRepository {

    override suspend fun registerUser(email: String, password: String): User {
        val registeredUser = network.registerUser(
            email = email,
            password = password
        )
        return registeredUser
    }
}