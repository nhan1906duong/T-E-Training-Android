package com.example.tetrainingandroid.ui.compose.ui.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tetrainingandroid.ui.compose.MainDestinations
import com.example.tetrainingandroid.ui.compose.profile.ProfileView
import com.example.tetrainingandroid.ui.compose.ui.home.HomeView
import com.example.tetrainingandroid.ui.compose.ui.search.SearchView

@Composable
fun MainView() {
    val tabs = remember {
        MainDestinations.values().map { it.route }
    }
    val navController = rememberNavController()
   Scaffold(
       bottomBar = { BottomBar(navController = navController, tabs = tabs) }
   ) {
       NavHost(
           navController = navController,
           startDestination = MainDestinations.HOME.route,
       ) {
           composable(MainDestinations.HOME.route) {
               HomeView()
           }
           composable(MainDestinations.SEARCH.route) {
               SearchView()
           }
           composable(MainDestinations.PROFILE.route) {
               ProfileView()
           }
       }
   }
}


@Composable
fun BottomBar(
    navController: NavController,
    tabs: List<String>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: return
    val routes = remember {
        MainDestinations.values().map { it.route }
    }

    if (currentRoute in routes) {
        BottomNavigation {
            tabs.forEach { title ->
                BottomNavigationItem(
                    selected = currentRoute == title,
                    onClick = {
                        if (currentRoute != title) {
                            navController.navigate(title) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {},
                    label = { Text(title) }
                )
            }
        }
    }
}