package com.example.tetrainingandroid.ui.compose.module.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED || this.lifecycle.currentState == Lifecycle.State.STARTED