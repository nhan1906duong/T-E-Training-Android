package com.example.tetrainingandroid.ui.compose.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.repo.MovieRepository
import com.example.tetrainingandroid.ui.compose.module.navigation.NavigationAction
import com.example.tetrainingandroid.ui.compose.module.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val repo: MovieRepository,
    ): ViewModel() {
    private val _trendingMovies = MutableLiveData<List<Movie>>()
    private val _popularMovies = MutableLiveData<List<Movie>>()
    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    private val _upComingMovies = MutableLiveData<List<Movie>>()

    private val _loading = MutableLiveData(false)

    val trendingMovies = _trendingMovies as LiveData<List<Movie>>
    val popularMovies = _popularMovies as LiveData<List<Movie>>
    val topRatedMovies = _topRatedMovies as LiveData<List<Movie>>
    val nowPlayingMovies = _nowPlayingMovies as LiveData<List<Movie>>
    val upComingMovies = _upComingMovies as LiveData<List<Movie>>

    init {
        loadData()
    }

    fun loadData() {
        val parentJob = viewModelScope.launch() {
            _loading.value = true
            supervisorScope {
                launch { _trendingMovies.value = repo.getTrending().results ?: listOf() }
                launch { _popularMovies.value = repo.getPopular().results ?: listOf() }
                launch { _topRatedMovies.value = repo.getTopRated().results ?: listOf() }
                launch { _nowPlayingMovies.value = repo.getNowPlaying().results ?: listOf() }
                launch { _upComingMovies.value = repo.getUpComing().results ?: listOf() }
            }
        }
        parentJob.invokeOnCompletion {
            _loading.value = false
        }
    }

    fun openDetail(movieId: Int) {
        viewModelScope.launch {
            navigationManager.submit(NavigationAction.createDetailAction(movieId))
        }
    }
}