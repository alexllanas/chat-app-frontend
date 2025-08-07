package com.chatappfrontend.common.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object ChatList : Screen("chat_list")
    data object NewMessage : Screen("new_message")
    data object Chat : Screen("chat/{chatId}/{userId}/{username}") {
        fun createRoute(chatId: String?, recipientId: String?, username: String?) = "chat/$chatId/$recipientId/$username"
    }
}