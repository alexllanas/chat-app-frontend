package com.chatappfrontend.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chatappfrontend.auth.ui.LoginScreen
import com.chatappfrontend.auth.ui.RegisterScreen
import com.chatappfrontend.common.navigation.Screen
import com.example.messages.ui.ChatListScreen
import com.example.messages.ui.ChatScreen
import com.example.messages.ui.NewMessageScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Login.route,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Login.route) { inclusive = false }
                    }
                },
                onLoginSuccess = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                navigateToLoginScreen = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onRegisterSuccess = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ChatList.route) {
            Log.d("ChatListScreen", "Navigating to ChatListScreen")
            ChatListScreen(
                onNewMessageClick = {
                    navController.navigate(Screen.NewMessage.route) {
                        popUpTo(Screen.ChatList.route) { inclusive = false }
                    }
                },
                onChatClick = { chatId, recipientId, username ->
                    navController.navigate(
                        Screen.Chat.createRoute(
                            chatId = chatId,
                            recipientId = recipientId,
                            username = username
                        )
                    ) {
                        popUpTo(Screen.ChatList.route) { inclusive = false }
                    }
                },
                onLogout = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.ChatList.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.NewMessage.route) {
            Log.d("NewMessageScreen", "Navigating to NewMessageScreen")
            NewMessageScreen(
                navigateToChat = { chatId, userId, username ->
                    navController.navigate(
                        Screen.Chat.createRoute(
                            chatId = chatId,
                            recipientId = userId,
                            username = username
                        )
                    ) {
                        popUpTo(Screen.ChatList.route) { inclusive = false }
                    }
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument("chatId") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("userId") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("username") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            Log.d(
                "ChatScreen",
                "Navigating to ChatScreen with arguments: ${backStackEntry.arguments}"
            )
            val chatId = backStackEntry.arguments?.getString("chatId")
            val userId = backStackEntry.arguments?.getString("userId")
            val username = backStackEntry.arguments?.getString("username")
            ChatScreen(
                chatId = chatId,
                userId = userId,
                username = username,
                onBackPressed = {
                    navController.navigate(Screen.ChatList.route) {
                        popUpTo(Screen.ChatList.route) { inclusive = false }
                    }
                },
            )
        }
    }
}