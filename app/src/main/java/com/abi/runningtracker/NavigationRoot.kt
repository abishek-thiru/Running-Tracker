package com.abi.runningtracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.abi.auth.presentation.intro.IntroScreenRoot
import com.abi.auth.presentation.register.RegisterScreenRoot

@Composable
fun NavigationRoot(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = "auth"
    ) {
        authGraph(navHostController)
    }
}

private fun NavGraphBuilder.authGraph(navHostController: NavHostController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable(route = "intro") {
            IntroScreenRoot(
                onSignUpClick = {
                    navHostController.navigate("register")
                },
                onSignInClick = {
                    navHostController.navigate("login")
                }
            )
        }
        composable(route = "register") {
            RegisterScreenRoot(
                onSignInClick = {
                    navHostController.navigate("login") {
                        popUpTo("register") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navHostController.navigate("login")
                }
            )
        }
    }
}