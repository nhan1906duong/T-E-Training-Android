package com.example.tetrainingandroid.ui.compose

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tetrainingandroid.ui.compose.theme.NetFakeTheme

@Composable
fun NetFakeApp(
    finishActivity: () -> Unit = {},
) {
    val tabs = remember {
        MainDestinations.values().map { it.route }
    }
    NetFakeTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {BottomBar(navController, tabs)},
        ) {
            NavGraph(
                finishActivity = finishActivity,
                navController = navController
            )
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
                                      launchSingleTop = true
                                  }
                              }
                    },
                    icon = {},
                    label = { Text(title)}
                )
            }
        }
    }
}