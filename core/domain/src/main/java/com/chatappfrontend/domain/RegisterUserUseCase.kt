package com.chatappfrontend.domain

import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.data.model.User
import com.chatappfrontend.data.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, email: String, password: String): ActionResult<User> {
        return authRepository.registerUser(
            username = username,
            email = email,
            password = password
        )
    }
}