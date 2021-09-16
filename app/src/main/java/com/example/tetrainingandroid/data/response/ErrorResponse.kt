package com.example.tetrainingandroid.data.response

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "status_code") val statusCode: Int? = null,
    @Json(name = "status_message") val statusMessage: String?,
    @Json(name = "success") val success: Boolean? = false,
) {
    companion object {
        fun emptyResponse() = ErrorResponse(statusMessage = "Empty response")
    }
}