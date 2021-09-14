package com.example.tetrainingandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetrainingandroid.repo.FakeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FakeViewModel @Inject constructor(
    private val repo: FakeRepository
): ViewModel() {
    private val _data = MutableLiveData<Int>()
    val data: LiveData<Int> = _data

    fun request() {
            viewModelScope.launch {
                repo.request()
        }
    }
}