package com.example.messages.viewmodel

import androidx.lifecycle.viewModelScope
import com.chatappfrontend.common.Resource
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.data.repository.DefaultUserRepository
import com.chatappfrontend.domain.GetUsersUseCase
import com.chatappfrontend.domain.model.User
import com.chatappfrontend.domain.repository.UserRepository
import com.chatappfrontend.ui.BaseViewModel
import com.example.messages.state.NewMessageUiState
import com.example.security.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewMessageViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(value = NewMessageUiState())
    val uiState: StateFlow<NewMessageUiState> = _uiState.asStateFlow()

    fun getUsers() {
        viewModelScope.launch {
            val userId = dataStoreManager.getUserId() ?: ""
            val result = userRepository.getUsers(currentUserId = userId)

            result.collect { resource ->
                when (resource) {
                    is Resource.Error<*> -> {}
                    is Resource.Loading<*> -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success<*> -> {
                        _uiState.update {
                            it.copy(
                                users = resource.data ?: listOf(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}
