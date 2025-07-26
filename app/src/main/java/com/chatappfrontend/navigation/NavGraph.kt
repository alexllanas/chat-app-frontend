package com.chatappfrontend.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chatappfrontend.auth.ui.LoginScreen
import com.chatappfrontend.auth.ui.RegisterScreen
import com.example.messages.ui.MessageListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate("register") {
                        popUpTo("login") { inclusive = false }
                    }
                },
                onLoginSuccess = { route ->
                    navController.navigate(route)
                }
            )
        }

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

        composable("message_list") {
             MessageListScreen(
                 onLogout = { route ->
                     navController.navigate(route)
                 }
             )
        }
    }
}