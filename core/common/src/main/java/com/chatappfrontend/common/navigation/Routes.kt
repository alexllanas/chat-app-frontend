package com.chatappfrontend.common.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object MessageList : Screen("message_list")
}