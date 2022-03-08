package com.example.tetrainingandroid.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), contain: @Composable () -> Unit) {
    MaterialTheme(
        //typography = ,
        shapes = Shapes,
        content = contain,
    )
}