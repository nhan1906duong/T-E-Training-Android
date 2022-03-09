package com.example.tetrainingandroid.ui.compose.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileView() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Text(text = "Profile")
        }
    }
}