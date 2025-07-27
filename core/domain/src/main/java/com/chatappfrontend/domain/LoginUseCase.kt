package com.chatappfrontend.domain

import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.data.model.User
import com.chatappfrontend.data.repository.AuthRepository
import javax.inject.Inject

/***
 * Use case for handling user login.
 *
 * This class isn't very useful right now. It only serves as a wrapper for now and only exists
 * in anticipation of future use cases that may require more complex logic or additional
 * dependencies.
 *
 * @property authRepository The repository that handles authentication operations.
 */
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): ActionResult<User> {
        return authRepository.login(
            email = email,
            password = password
        )
    }
}