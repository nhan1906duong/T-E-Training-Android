package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class SpokenLanguage(
    @Json(name = "iso_639_1") val iso639_1: String?,
    @Json(name = "name") val name: String?,
)