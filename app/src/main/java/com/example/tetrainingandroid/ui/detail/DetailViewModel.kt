package com.example.tetrainingandroid.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.architecture.ExceptionHandler
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    exceptionHandler: ExceptionHandler,
    private val repo: MovieRepository,
) : BaseViewModel(exceptionHandler) {

    private val _movie = MutableLiveData<Movie>()
    val movie = _movie as LiveData<Movie>

    init {
        getDetail()
    }

    private fun getDetail() {
        viewModelScope.launch(exceptionHandler.handler) {
            val result = (async { repo.getDetail(3) }).await()
            _movie.value = result
        }
    }
}