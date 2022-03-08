package com.example.tetrainingandroid.ui.compose.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.example.tetrainingandroid.ui.compose.theme.SecondaryColor
import com.example.tetrainingandroid.ui.compose.theme.TertiaryColor

@Composable
fun GradientButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    onClick: () -> Unit = { },
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = Modifier
                .background(Brush.horizontalGradient(listOf(TertiaryColor, SecondaryColor)))
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text.uppercase(), color = Color.White)
        }
    }
}