package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class Career(
    @Json(name = "id") val id: Int?,
    @Json(name = "title") val title: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "character") val character: String?,
    @Json(name = "job") val job: String?,
)