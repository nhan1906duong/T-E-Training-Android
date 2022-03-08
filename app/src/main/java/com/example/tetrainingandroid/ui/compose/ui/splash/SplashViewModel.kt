package com.example.tetrainingandroid.ui.compose.ui.splash

import androidx.lifecycle.*
import com.example.tetrainingandroid.repo.AuthenticationRepository
import com.example.tetrainingandroid.ui.splash.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repo: AuthenticationRepository
): ViewModel() {

    private val _loginData = MediatorLiveData<LoginState>()
    val loginData: LiveData<LoginState> = _loginData

    init {
        getTokenRequest()
    }

    private fun getTokenRequest() {
        viewModelScope.launch {
            repo.checkLogin()
        }

        _loginData.addSource(repo.loginState) {
            _loginData.value = it
            if (it != LoginState.Initialize) _loginData.removeSource(repo.loginState)
        }
    }
}