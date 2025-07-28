package com.chatappfrontend.domain

import com.chatappfrontend.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
){

    suspend operator fun invoke() {
        authRepository.logout()
    }
}