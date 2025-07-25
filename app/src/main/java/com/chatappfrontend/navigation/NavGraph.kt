package com.chatappfrontend.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chatappfrontend.auth.ui.LoginScreen
import com.chatappfrontend.auth.ui.RegisterScreen
import com.example.messages.ui.MessageListScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination = "register") {

        composable("register") {
            RegisterScreen(
                navigateToLoginScreen = {
                    navController.navigate("login")
                },
                onRegisterSuccess = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable("login") {
            LoginScreen(
                onLoginClicked = { email, password -> },
                onNavigateToRegister = {
                    navController.navigate("register") {
                        popUpTo("login") { inclusive = false }
                    }
                }
            )
        }

        composable("message_list") {
             MessageListScreen(
                 onLogout = { route ->
                     navController.navigate(route)
                 }
             )
        }
    }
}