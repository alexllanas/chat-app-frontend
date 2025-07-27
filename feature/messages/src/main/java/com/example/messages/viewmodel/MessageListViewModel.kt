package com.example.messages.viewmodel

import androidx.lifecycle.viewModelScope
import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.common.navigation.Screen
import com.chatappfrontend.domain.LogoutUseCase
import com.chatappfrontend.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageListViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    fun logout() {
        viewModelScope.launch {

            try {
                logoutUseCase.invoke()
                emitUiEvent(event = UiEvent.Navigate(Screen.Login.route))
            } catch (e: Exception) {
//                emitUiEvent(event = UiEvent.ShowError(message = e.message ?: "An error occurred"))
            }
        }
    }
}
