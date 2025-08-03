package com.chatappfrontend.domain

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.User
import com.chatappfrontend.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): ResultWrapper<User> {
        return authRepository.login(
            email = email,
            password = password
        )
    }
}