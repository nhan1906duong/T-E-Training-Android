package com.example.tetrainingandroid.ui.splash

import androidx.lifecycle.*
import com.example.tetrainingandroid.architecture.BaseViewModel
import com.example.tetrainingandroid.repo.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repo: AuthenticationRepository
): BaseViewModel() {

    private val _loginData = MediatorLiveData<LoginState>()
    val loginData: LiveData<LoginState> = _loginData

    override fun loadData() {
        super.loadData()
        getTokenRequest()
    }

    private fun getTokenRequest() {
        viewModelScope.launch(getHandler()) {
            repo.checkLogin()
        }

        _loginData.addSource(repo.loginState) {
            _loginData.value = it
            if (it != LoginState.Initialize) _loginData.removeSource(repo.loginState)
        }
    }
}