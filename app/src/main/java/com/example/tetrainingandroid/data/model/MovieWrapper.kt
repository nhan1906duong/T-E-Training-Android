package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class MovieWrapper(
    @Json(name = "cast") val cast: List<Movie>?,
    @Json(name = "crew") val crew: List<Movie>?,
)