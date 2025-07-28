package com.chatappfrontend.common.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object ConversationList : Screen("conversation_list")
    data object NewMessage : Screen("new_message")
    data object Conversation : Screen("conversation/{userId}") {
        fun createRoute(userId: String) = "conversation/$userId"
    }
}