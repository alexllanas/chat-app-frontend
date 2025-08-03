package com.chatappfrontend.domain

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.User
import com.chatappfrontend.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, email: String, password: String): ResultWrapper<User> {
        return authRepository.registerUser(
            username = username,
            email = email,
            password = password
        )
    }
}