package com.example.tetrainingandroid.ui.compose.ui.detail

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tetrainingandroid.R

@Composable
fun DetailView(
    movieId: Int,
    navController: NavController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = movieId) {
        detailViewModel.getDetail(movieId)
    }
    val movie by detailViewModel.movie.observeAsState()
    Scaffold {
        Text(text = movie?.title ?: stringResource(id = R.string.unknown))
    }
}