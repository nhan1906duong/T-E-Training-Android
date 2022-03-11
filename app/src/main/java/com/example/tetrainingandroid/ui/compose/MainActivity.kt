package com.example.tetrainingandroid.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tetrainingandroid.ui.compose.module.navigation.*
import com.example.tetrainingandroid.ui.compose.theme.NetFakeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @Inject lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetFakeTheme {
                val navController = rememberNavController()
                NavHandle(navController)
                NavGraph(navController)
            }
        }
    }

    @Composable
    private fun NavHandle(navController: NavHostController) {
        navigationManager.actions.collectAsState(null).value.also { action ->
            val currentEntry = navController.currentBackStackEntry
            if (currentEntry != null && action != null) {
                NavigationAction.action(action, navController)
            }
        }
    }
}