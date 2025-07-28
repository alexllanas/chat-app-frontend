package com.chatappfrontend.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chatappfrontend.auth.ui.LoginScreen
import com.chatappfrontend.auth.ui.RegisterScreen
import com.chatappfrontend.common.navigation.Screen
import com.example.messages.ui.ConversationListScreen
import com.example.messages.ui.ConversationScreen
import com.example.messages.ui.NewMessageScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo("login") { inclusive = false }
                    }
                },
                onLoginSuccess = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                navigateToLoginScreen = {
                    navController.navigate(Screen.Login.route)
                },
                onRegisterSuccess = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(Screen.ConversationList.route) {
            ConversationListScreen(
                onNewMessageClick = {
                    navController.navigate(Screen.NewMessage.route)
                },
                onConversationClick = { userId ->
                    navController.navigate("conversation/$userId")
                },
                onLogout = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(Screen.NewMessage.route) {
            NewMessageScreen(
                navigateToConversation = { userId ->
                    navController.navigate("conversation/$userId")
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Conversation.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId != null) {
                ConversationScreen(
                    userId = userId,
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}