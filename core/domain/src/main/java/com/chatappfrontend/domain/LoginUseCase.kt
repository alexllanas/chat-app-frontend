package com.chatappfrontend.domain

import com.chatappfrontend.common.NetworkResult
import com.chatappfrontend.data.repository.AuthRepository
import com.example.model.User
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): NetworkResult<User> {
        return authRepository.login(
            email = email,
            password = password
        )
    }
}