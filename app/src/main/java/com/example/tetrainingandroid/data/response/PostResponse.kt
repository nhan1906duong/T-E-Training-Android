package com.example.tetrainingandroid.data.response

import com.squareup.moshi.Json

data class PostResponse(
    @Json(name = "success") val success: Boolean?,
    @Json(name = "status_code") val statusCode: Int?,
    @Json(name = "status_message") val statusMessage: String?,
)