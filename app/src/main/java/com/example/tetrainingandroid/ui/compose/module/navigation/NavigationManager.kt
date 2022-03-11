package com.example.tetrainingandroid.ui.compose.module.navigation

import kotlinx.coroutines.flow.MutableSharedFlow

class NavigationManager {
    val actions = MutableSharedFlow<NavigationAction>()

    suspend fun submit(action: NavigationAction) {
        actions.emit(action)
    }
}