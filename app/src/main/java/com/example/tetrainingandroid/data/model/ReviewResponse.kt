package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class ReviewResponse(
    @Json(name = "id") val id: Int?,
    @Json(name = "page") val page: Int?,
    @Json(name = "result") val results: List<Review>?,
    @Json(name = "total_pages") val totalPages: Int?,
    @Json(name = "total_result") val totalResult: Int?,
)