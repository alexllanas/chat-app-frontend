package com.chatappfrontend.domain

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.User
import com.chatappfrontend.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): ResultWrapper<List<User>> {
        return userRepository.getUsers()
    }
}