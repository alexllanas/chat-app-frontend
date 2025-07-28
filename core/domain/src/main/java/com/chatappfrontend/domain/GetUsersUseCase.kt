package com.chatappfrontend.domain

import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.domain.model.User
import com.chatappfrontend.domain.repository.AuthRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): ActionResult<List<User>> {
        return authRepository.getUsers()
    }
}