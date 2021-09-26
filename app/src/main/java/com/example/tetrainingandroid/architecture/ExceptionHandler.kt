package com.example.tetrainingandroid.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.tetrainingandroid.data.response.ErrorResponse
import com.example.tetrainingandroid.data.storage.StorageHelper
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import javax.inject.Inject

enum class HttpResponseStatusCode(val code: Int) {
    Success(200),
    Unauthorized(401),
}

class ExceptionHandler @Inject constructor(private val moshi: Moshi) {
    private val error = MutableLiveData<ErrorResponse?>()
    private val _authorizeState = MutableLiveData(true)

    val errorMessage: LiveData<String?> = Transformations.map(error) { it?.statusMessage }
    val authorizeState: LiveData<Boolean> = _authorizeState

    val handler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is HttpException -> {
                handleErrorRequest(throwable)
            }
            else -> error.value = ErrorResponse.otherError(throwable.message)
        }
    }


    private fun handleErrorRequest(exception: HttpException) {
        if (exception.code() == HttpResponseStatusCode.Unauthorized.code) {
            _authorizeState.value = false
        }
        val message = exception.response()?.errorBody()?.source()
        if (message == null) {
            error.value = ErrorResponse.emptyResponse()
            return
        }
        val errorResponse = moshi.adapter(ErrorResponse::class.java).fromJson(message)
        error.value = errorResponse
    }
}