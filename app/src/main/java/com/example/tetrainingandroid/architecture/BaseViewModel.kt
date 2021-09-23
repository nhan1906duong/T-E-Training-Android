package com.example.tetrainingandroid.architecture

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.cancel
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    // Avoid call exception Handler in it
    @Inject protected lateinit var exceptionHandler: ExceptionHandler

    fun getError() = exceptionHandler.errorMessage
    protected fun getHandler() = exceptionHandler.handler

    override fun onCleared() {
        super.onCleared()
        exceptionHandler.handler.cancel()
    }

    open fun loadData() {

    }
} 