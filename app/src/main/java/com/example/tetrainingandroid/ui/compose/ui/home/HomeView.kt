package com.example.tetrainingandroid.ui.compose.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tetrainingandroid.R
import com.example.tetrainingandroid.ui.compose.ui.common.MovieSectionView

@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val nowPlaying by viewModel.nowPlayingMovies.observeAsState(initial = null)
    val topRated by viewModel.topRatedMovies.observeAsState(initial = null)
    val popular by viewModel.popularMovies.observeAsState(initial = null)
    val upComing by viewModel.upComingMovies.observeAsState(initial = null)
    val onClick: (Int) -> Unit = {
        viewModel.openDetail(it)
    }
    Scaffold {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            if (popular?.isNotEmpty() == true) MovieSectionView(
                title = R.string.popular_movies,
                movies = popular!!,
                onClick
            )
            if (nowPlaying?.isNotEmpty() == true) MovieSectionView(
                title = R.string.now_playing_movies,
                movies = nowPlaying!!,
                onClick
            )
            if (topRated?.isNotEmpty() == true) MovieSectionView(
                title = R.string.top_rated_movies,
                movies = topRated!!,
                onClick
            )
            if (upComing?.isNotEmpty() == true) MovieSectionView(
                title = R.string.up_coming_movies,
                movies = upComing!!,
                onClick
            )
        }
    }
}

