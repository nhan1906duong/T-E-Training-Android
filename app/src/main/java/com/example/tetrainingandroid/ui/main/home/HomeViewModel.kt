package com.example.tetrainingandroid.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.architecture.ExceptionHandler
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(exceptionHandler: ExceptionHandler, private val repo: MovieRepository): BaseViewModel(exceptionHandler) {
    private val _popularMovies = MutableLiveData<List<Movie>>()
    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    private val _upComingMovies = MutableLiveData<List<Movie>>()

    val popularMovies = _popularMovies as LiveData<List<Movie>>
    val topRatedMovies = _topRatedMovies as LiveData<List<Movie>>
    val nowPlayingMovies = _nowPlayingMovies as LiveData<List<Movie>>
    val upComingMovies = _upComingMovies as LiveData<List<Movie>>

    init {
        loadData()
    }

    private fun loadData() {
        getPopular()
        getTopRated()
        getNowPlaying()
        getUpComing()
    }

    private fun getPopular() {
        viewModelScope.launch(exceptionHandler.handler) {
            _popularMovies.value = repo.getPopular()
        }
    }

    private fun getTopRated() {
        viewModelScope.launch(exceptionHandler.handler) {
            _topRatedMovies.value = repo.getTopRated()
        }
    }

    private fun getNowPlaying() {
        viewModelScope.launch(exceptionHandler.handler) {
            _nowPlayingMovies.value = repo.getNowPlaying()
        }
    }

    private fun getUpComing() {
        viewModelScope.launch(exceptionHandler.handler) {
            _upComingMovies.value = repo.getUpComing()
        }
    }
}