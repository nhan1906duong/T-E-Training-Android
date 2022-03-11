package com.example.tetrainingandroid.ui.compose.module.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.tetrainingandroid.ui.compose.ui.detail.DetailView
import com.example.tetrainingandroid.ui.compose.ui.login.LoginView
import com.example.tetrainingandroid.ui.compose.ui.main.MainView
import com.example.tetrainingandroid.ui.compose.ui.splash.SplashView

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = StartDirections.route.destination
    ) {
        navigation(
            route = StartDirections.route.destination,
            startDestination = StartDirections.root.destination
        ) {
            composable(StartDirections.root.destination) {
                SplashView()
            }
            composable(StartDirections.login.destination) {
                LoginView()
            }
        }
        navigation(
            route = MainDirections.route.destination,
            startDestination = MainDirections.root.destination
        ) {
            composable(MainDirections.root.destination) {
                MainView()
            }
            composable(
                MainDirections.detail.destination,
                arguments = MainDirections.detail.arguments,
            ) { entry ->
                val args = requireNotNull(entry.arguments)
                val movieId = args.getInt("movieId")
                DetailView(movieId)
            }
        }
    }
}