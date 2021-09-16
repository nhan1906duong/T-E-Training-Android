package com.example.tetrainingandroid.ui.splash

import androidx.lifecycle.*
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.architecture.ExceptionHandler
import com.example.tetrainingandroid.data.storage.StorageHelper
import com.example.tetrainingandroid.repo.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repo: AuthenticationRepository,
    private val sessionHelper: StorageHelper,
    exceptionHandler: ExceptionHandler
): BaseViewModel(exceptionHandler) {
    private val _data = MediatorLiveData<Boolean>()
    val data: LiveData<Boolean> = _data

    fun getTokenRequest() {
        viewModelScope.launch(exceptionHandler.handler) {
            repo.getSession()
            _data.value = true
        }
        _data.addSource(exceptionHandler.errorMessage) {
            sessionHelper.removeAll()
            _data.value = false
            _data.removeSource(exceptionHandler.errorMessage)
        }
    }
}