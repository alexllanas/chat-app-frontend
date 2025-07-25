package com.chatappfrontend.domain

import com.chatappfrontend.common.Result
import com.chatappfrontend.data.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
){

    suspend operator fun invoke(): Result {
        return authRepository.logout()
    }
}