package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class Image(
    @Json(name = "aspect_ratio") val aspectRatio: Int?,
    @Json(name = "file_path") val filePath: String?,
    @Json(name = "height") val height: Int?,
    @Json(name = "iso_639_1") val iso639_1: Int?,
    @Json(name = "vote_average") val voteAverage: Int?,
    @Json(name = "vote_count") val voteCount: Int?,
    @Json(name = "width") val width: Int?,
)