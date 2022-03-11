package com.example.tetrainingandroid.ui.compose.ui.splash

import androidx.lifecycle.*
import com.example.tetrainingandroid.repo.AuthenticationRepository
import com.example.tetrainingandroid.ui.compose.module.navigation.MainDirections
import com.example.tetrainingandroid.ui.compose.module.navigation.NavigationAction
import com.example.tetrainingandroid.ui.compose.module.navigation.NavigationManager
import com.example.tetrainingandroid.ui.compose.module.navigation.StartDirections
import com.example.tetrainingandroid.ui.splash.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repo: AuthenticationRepository,
    private val navigationManager: NavigationManager,
): ViewModel() {
    private val _loginState = MutableStateFlow(LoginState.Initialize)

    init {
        viewModelScope.launch {
            repo.checkLogin()
            _loginState.emit(repo.checkLogin())
        }
        _loginState.onEach {
            when (it) {
                LoginState.Login -> navigationManager.submit(NavigationAction.createMainAction())
                LoginState.ApiAuthorization -> navigationManager.submit(NavigationAction.createLoginAction())
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}