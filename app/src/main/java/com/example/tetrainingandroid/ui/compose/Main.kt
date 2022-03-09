package com.example.tetrainingandroid.ui.compose

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tetrainingandroid.ui.compose.profile.ProfileView
import com.example.tetrainingandroid.ui.compose.ui.home.HomeView
import com.example.tetrainingandroid.ui.compose.ui.search.SearchView


fun NavGraphBuilder.main(
    openDetail: (Int, NavBackStackEntry) -> Unit,
) {
    composable(MainDestinations.HOME.route) { navBackStackEntry ->
        HomeView(
            openDetail = { movieId ->
                openDetail(movieId, navBackStackEntry)
            }
        )
    }
    composable(MainDestinations.SEARCH.route) {
        SearchView()
    }
    composable(MainDestinations.PROFILE.route) {
        ProfileView()
    }
}

enum class MainDestinations(val route: String) {
    HOME("main/home"),
    SEARCH("main/search"),
    PROFILE("main/profile")
}