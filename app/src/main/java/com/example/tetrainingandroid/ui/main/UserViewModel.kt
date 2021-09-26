package com.example.tetrainingandroid.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.data.model.User
import com.example.tetrainingandroid.data.response.PostResponse
import com.example.tetrainingandroid.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: UserRepository
): BaseViewModel() {
    val user: LiveData<User> = repo.user

    fun getUser() {
        viewModelScope.launch(getHandler()) {
            repo.getUser()
        }
    }

    fun addToWatchList(movieId: Int): LiveData<PostResponse?> {
        val result = MutableLiveData<PostResponse?>()
        viewModelScope.launch(getHandler()) {
            result.value = repo.addToWatchList(movieId)
        }
        return result
    }

    fun markFavorite(movieId: Int): LiveData<PostResponse?> {
        val result = MutableLiveData<PostResponse?>()
        viewModelScope.launch(getHandler()) {
            val response = repo.markFavorite(movieId)
            result.value = response
        }
        return result
    }

    fun postComment(movieId: Int, rating: Float?, content: String?): LiveData<PostResponse> {
        val result = MutableLiveData<PostResponse>()
        viewModelScope.launch(getHandler()) {
            result.value = repo.postReview(movieId, rating, content)
        }
        return result
    }
}