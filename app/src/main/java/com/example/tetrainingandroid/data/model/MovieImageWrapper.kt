package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class MovieImageWrapper(
    @Json(name = "backdrops") val backdrops: List<Image>?,
    @Json(name = "posters") val posters: List<Image>?,
)