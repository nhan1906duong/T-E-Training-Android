package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class CareerWrapper(
    @Json(name = "cast") val cast: List<Career>?,
    @Json(name = "crew") val crew: List<Career>?,
)