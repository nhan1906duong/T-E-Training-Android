package com.example.tetrainingandroid.architecture

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.cancel

abstract class BaseViewModel(val exceptionHandler: ExceptionHandler): ViewModel() {
    override fun onCleared() {
        super.onCleared()
        exceptionHandler.handler.cancel()
    }
} 