package com.chatappfrontend.common

sealed class UiEvent {
    class Navigate(val route: String) : UiEvent()
    class ShowSnackbar(val message: String) : UiEvent()
}