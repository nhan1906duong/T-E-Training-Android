package com.example.tetrainingandroid.ui.compose.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.ui.compose.module.navigation.NavigationAction
import com.example.tetrainingandroid.ui.compose.module.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
): ViewModel() {

    fun openMain() {
        viewModelScope.launch {
           navigationManager.submit(NavigationAction.createMainAction())
        }
    }
}