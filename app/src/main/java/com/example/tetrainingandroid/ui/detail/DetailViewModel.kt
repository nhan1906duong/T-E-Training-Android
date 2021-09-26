package com.example.tetrainingandroid.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: MovieRepository,
) : BaseViewModel() {

    private val movieId: Int = savedStateHandle["movieId"] ?: throw IllegalArgumentException("missing movieId")

    private val _movie = MutableLiveData<Movie>()
    val movie = _movie as LiveData<Movie>

    override fun loadData() {
        super.loadData()
        getDetail()
    }

    fun refresh() {
        getDetail()
    }

    private fun getDetail() {
        viewModelScope.launch(getHandler()) {
            val result = (async { repo.getDetail(movieId) }).await()
            _movie.value = result
        }
    }
}