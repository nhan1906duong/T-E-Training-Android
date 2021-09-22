package com.example.tetrainingandroid.ui.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.architecture.ExceptionHandler
import com.example.tetrainingandroid.data.model.Youtube
import com.example.tetrainingandroid.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YoutubeViewModel @Inject constructor(
    exceptionHandler: ExceptionHandler,
    private val repo: MovieRepository,
): BaseViewModel(exceptionHandler) {
    private val _videos = MutableLiveData<List<Youtube>>()
    val videos = _videos as LiveData<List<Youtube>>
    private var movieId: Int = 0

    fun setMovie(movieId: Int) {
        this.movieId = movieId
        getTrailers()
    }

    private fun getTrailers() {
        viewModelScope.launch(exceptionHandler.handler) {
            val result = (async { repo.getTrailers(movieId) }).await()
            _videos.value = result
        }
    }
}