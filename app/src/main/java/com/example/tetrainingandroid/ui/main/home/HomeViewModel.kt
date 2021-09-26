package com.example.tetrainingandroid.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.repo.MovieRepository
import com.example.tetrainingandroid.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: MovieRepository,
    private val userRepo: UserRepository,
    ): BaseViewModel() {
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

    val loading = _loading as LiveData<Boolean>

    fun refresh() = loadData()

    override fun loadData() {
        val parentJob = viewModelScope.launch(getHandler()) {
            _loading.value = true
            supervisorScope {
                launch { _trendingMovies.value = repo.getTrending() }
                launch { _popularMovies.value = repo.getPopular() }
                launch { _topRatedMovies.value = repo.getTopRated() }
                launch { _nowPlayingMovies.value = repo.getNowPlaying() }
                launch { _upComingMovies.value = repo.getUpComing() }
            }
        }
        parentJob.invokeOnCompletion {
            _loading.value = false
        }
    }
}