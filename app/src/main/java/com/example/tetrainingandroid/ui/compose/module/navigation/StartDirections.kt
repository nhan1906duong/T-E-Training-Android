package com.example.tetrainingandroid.ui.compose.module.navigation

import androidx.navigation.NamedNavArgument

object StartDirections {

    val route = object: NavigationCommand {

        override val arguments: List<NamedNavArgument> = emptyList()

        override val destination: String = "start-route"

    }

    val root = object: NavigationCommand {

        override val arguments: List<NamedNavArgument> = emptyList()

        override val destination: String = "splash"

    }

    val login = object: NavigationCommand {

        override val arguments: List<NamedNavArgument> = emptyList()

        override val destination: String = "login"

    }
}