package com.chatappfrontend.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
                onChatClick = { chatId, userId ->
                    navController.navigate(
                        Screen.Chat.createRoute(chatId = chatId, userId = userId)
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
                navigateToChat = { chatId, userId ->
                    navController.navigate(
                        Screen.Chat.createRoute(
                            chatId = chatId,
                            userId = userId
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
                },
                navArgument("userId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            Log.d("ChatScreen", "Navigating to ChatScreen with arguments: ${backStackEntry.arguments}")
            val chatId = backStackEntry.arguments?.getString("chatId")
            val userId = backStackEntry.arguments?.getString("userId")
            ChatScreen(
                chatId = chatId,
                userId = userId,
                onBackPressed = {
                    navController.navigate(Screen.ChatList.route) {
                        popUpTo(Screen.ChatList.route) { inclusive = false }
                    }
                },
            )
        }
    }
}