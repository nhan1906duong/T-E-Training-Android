package com.example.tetrainingandroid.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tetrainingandroid.ui.compose.ui.detail.DetailView
import com.example.tetrainingandroid.ui.compose.ui.login.LoginView
import com.example.tetrainingandroid.ui.compose.ui.splash.SplashView

enum class AppDestinations(val route: String) {
    SPLASH("splash"),
    LOGIN("login"),
    MAIN("main"),
    DETAIL("movie/{id}")
}

class AppActions(navController: NavController) {
    val onSplashLoadingComplete = { isAuth: Boolean, from: NavBackStackEntry ->
        if (from.lifecycleIsResumed()) {
            if (isAuth) {
                navController.navigate(
                    AppDestinations.MAIN.route,
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(AppDestinations.SPLASH.route, inclusive = true)
                        .setLaunchSingleTop(true)
                        .build()
                )
            } else {
                navController.navigate(
                    AppDestinations.LOGIN.route,
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(AppDestinations.SPLASH.route, inclusive = true)
                        .setLaunchSingleTop(true)
                        .build()
                )
            }
        }
    }

    val onLogin = { from: NavBackStackEntry ->
        if (from.lifecycleIsResumed()) {
            navController.navigate(
                AppDestinations.MAIN.route,
                navOptions = NavOptions.Builder()
                    .setPopUpTo(AppDestinations.LOGIN.route, inclusive = true)
                    .setLaunchSingleTop(true)
                    .build()
            )
        }
    }

    val openMovie = { movieId: Int, from: NavBackStackEntry ->
        if (from.lifecycleIsResumed()) {
            navController.navigate(AppDestinations.DETAIL.route.replace("{id}", "$movieId"))
        }
    }
}

@Composable
fun NavGraph(
    finishActivity: () -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    val actions = remember(navController) {
        AppActions(navController)
    }
    NavHost(
        navController = navController,
        startDestination = AppDestinations.SPLASH.route,
    ) {
        composable(AppDestinations.SPLASH.route) { navBackStackEntry ->
            SplashView(
                onLoadingFinish = { auth ->
                    actions.onSplashLoadingComplete(auth, navBackStackEntry)
                }
            )
        }
        composable(AppDestinations.LOGIN.route) { navBackStackEntry ->
            LoginView(
                onLoginSuccess = {
                    actions.onLogin(navBackStackEntry)
                }
            )
        }
        navigation(
            route = AppDestinations.MAIN.route,
            startDestination = MainDestinations.HOME.route,
        ) {
            main(
                openDetail = actions.openMovie
            )
        }
        composable(
            AppDestinations.DETAIL.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType}
            )
        ) { navBackStackEntry ->
            val arguments = requireNotNull(navBackStackEntry.arguments)
            val movieId = arguments.getInt("id")
            DetailView(movieId = movieId)
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED