package com.example.tetrainingandroid.ui.compose.ui.detail

import androidx.lifecycle.*
import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: MovieRepository,
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie = _movie as LiveData<Movie>

    fun getDetail(movieId: Int) {
        viewModelScope.launch() {
            _movie.value = repo.getDetail(movieId)
        }
    }
}