package com.example.tetrainingandroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.data.storage.RequestTokenResponseStorage
import com.example.tetrainingandroid.data.storage.SessionStorage
import com.example.tetrainingandroid.repo.FakeRepository
import com.example.tetrainingandroid.validate.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repo: FakeRepository
): ViewModel() {
    private val _data = MutableLiveData<Int>()
    val data: LiveData<Int> = _data

    fun getTokenRequest() {
        viewModelScope.launch {
        }
    }
}