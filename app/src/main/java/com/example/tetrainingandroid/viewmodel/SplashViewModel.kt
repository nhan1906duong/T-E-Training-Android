package com.example.tetrainingandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.repo.AuthenticationFakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repo: AuthenticationFakeRepository
): ViewModel() {
    private val _data = MutableLiveData<Boolean>(null)
    val data: LiveData<Boolean> = _data

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("ERROR_TAG", throwable.message ?: "Undefined")
        _data.value = false
    }

    fun getTokenRequest() {
        viewModelScope.launch(exceptionHandler) {
            repo.getRequestToken()
            _data.value = true
        }
    }
}