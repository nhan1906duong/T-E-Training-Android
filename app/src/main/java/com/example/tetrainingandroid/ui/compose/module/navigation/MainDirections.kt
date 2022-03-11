package com.example.tetrainingandroid.ui.compose.module.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object MainDirections {

    val route = object: NavigationCommand {

        override val arguments: List<NamedNavArgument> = emptyList()

        override val destination: String = "main-route"

    }

    val root = object: NavigationCommand {

        override val arguments: List<NamedNavArgument> = emptyList()

        override val destination: String = "main"

    }

    val detail = object: NavigationCommand {

        override val arguments: List<NamedNavArgument> = listOf(navArgument("movieId") { type = NavType.IntType})

        override val destination: String = "detail/{movieId}"

    }
}