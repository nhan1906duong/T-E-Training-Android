package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class MovieVideoWrapper(
    @Json(name = "results") val results: List<MovieVideo>?
)