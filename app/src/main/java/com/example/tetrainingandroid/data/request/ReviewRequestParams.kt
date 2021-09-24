package com.example.tetrainingandroid.data.request

import com.squareup.moshi.Json

data class ReviewRequestParams(
    @Json(name = "value") val value: Float?,
    @Json(name = "content") val content: String?,
)