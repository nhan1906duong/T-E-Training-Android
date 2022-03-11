package com.example.tetrainingandroid.ui.compose.module.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

sealed class NavigationAction {
    data class Push(
        val command: NavigationCommand,
        val builder: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction()

    data class PushReplacement(
        val command: NavigationCommand,
        val builder: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction()

    data class PushAndRemoveUntil(
        val command: NavigationCommand,
        val until: String,
        val builder: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction()

    data class PushAndRemoveTo(
        val command: NavigationCommand,
        val to: String,
        val builder: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction()

    data class Start(
        val command: NavigationCommand,
        val builder: NavOptionsBuilder.() -> Unit = {}
    ): NavigationAction()

    object Pop : NavigationAction()
    object Up : NavigationAction()
    class PopUntil(val destination: String) : NavigationAction()
    class PopTo(val destination: String) : NavigationAction()

    companion object {
        fun createLoginAction(): NavigationAction {
            return Start(StartDirections.login)
        }
        fun createMainAction(): NavigationAction {
            return Start(MainDirections.root)
        }
        fun createDetailAction(movieId: Int): NavigationAction {
            return Push(object: NavigationCommand {
                override val arguments = MainDirections.detail.arguments
                override val destination = MainDirections.detail.destination.replace("{movieId}", movieId.toString())

            })
        }

        fun action(action: NavigationAction, navController: NavController) {
            when (action) {
                is Push -> {
                    navController.navigate(action.command.destination) {
                        apply(action.builder)
                    }
                }
                is PushReplacement -> {
                    navController.navigate(action.command.destination) {
                        apply(action.builder)
                        navController.currentDestination?.route?.let {
                            popUpTo(it) {
                                inclusive = true
                            }
                        }
                    }
                }
                is PushAndRemoveUntil -> {
                    navController.navigate(action.command.destination) {
                        apply(action.builder)
                        popUpTo(action.until)
                    }
                }
                is PushAndRemoveTo -> {
                    navController.navigate(action.command.destination) {
                        apply(action.builder)
                        popUpTo(action.to) {
                            inclusive = true
                        }
                    }
                }
                is Start -> {
                    navController.navigate(action.command.destination) {
                        apply(action.builder)
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
                is Up -> {
                    navController.navigateUp()
                }
                is Pop -> {
                    navController.popBackStack()
                }
                is PopTo -> {
                    navController.popBackStack(route = action.destination, inclusive = true)
                }
                is PopUntil -> {
                    navController.popBackStack(route = action.destination, inclusive = false)
                }
            }
        }
    }
}