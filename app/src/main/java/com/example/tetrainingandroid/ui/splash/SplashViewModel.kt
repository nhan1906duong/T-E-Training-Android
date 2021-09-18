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

    private val _loginData = MediatorLiveData<Boolean>()
    val loginData: LiveData<Boolean> = _loginData

    init {
        getTokenRequest()
    }

    private fun getTokenRequest() {
        viewModelScope.launch(exceptionHandler.handler) {
            repo.getSession()
            _loginData.value = true
        }
        _loginData.addSource(exceptionHandler.errorMessage) {
            sessionHelper.removeAll()
            _loginData.value = false
            _loginData.removeSource(exceptionHandler.errorMessage)
        }
    }
}