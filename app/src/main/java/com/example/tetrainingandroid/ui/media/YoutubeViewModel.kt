package com.example.tetrainingandroid.ui.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.data.model.Youtube
import com.example.tetrainingandroid.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YoutubeViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val repo: MovieRepository,
): BaseViewModel() {
    private val movieId: Int = stateHandle["movieId"] ?: throw IllegalArgumentException("Missing movie id")

    private val _videos = MutableLiveData<List<Youtube>>()
    val videos = _videos as LiveData<List<Youtube>>

    override fun loadData() {
        super.loadData()
        getTrailers()
    }

    private fun getTrailers() {
        viewModelScope.launch(getHandler()) {
            val result = (async { repo.getTrailers(movieId) }).await()
            _videos.value = result
        }
    }
}