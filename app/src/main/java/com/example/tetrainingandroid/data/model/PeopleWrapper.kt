package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class PeopleWrapper(
    @Json(name = "cast") val cast: List<People>?,
    @Json(name = "crew") val crew: List<People>?,
)