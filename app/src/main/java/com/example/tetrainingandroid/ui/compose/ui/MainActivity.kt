package com.example.tetrainingandroid.ui.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tetrainingandroid.ui.compose.ui.detail.DetailView
import com.example.tetrainingandroid.ui.compose.theme.AppTheme
import com.example.tetrainingandroid.ui.compose.ui.login.LoginView
import com.example.tetrainingandroid.ui.compose.ui.home.HomeView
import com.example.tetrainingandroid.ui.compose.ui.splash.SplashView
import dagger.hilt.android.AndroidEntryPoint

enum class MainDestinations(val route: String) {
    SPLASH("splash"),
    LOGIN("login"),
    MAIN("main"),
    DETAIL("detail/{id}")
}

@Composable
fun NavGraph() {

}

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppTheme {
                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashView(navController)
                    }
                    composable("login") {
                        LoginView(navController)
                    }
                    composable("home") {
                        HomeView(navController)
                    }
                    composable("detail/{id}",
                        arguments = listOf(
                            navArgument("id") { type = NavType.IntType}
                        )
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getInt("id") ?: return@composable
                        DetailView(movieId, navController)
                    }
                }
            }
        }

    }
}