package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class ProductionCountry(
    @Json(name = "iso_3166_1") val iso3166_1: String?,
    @Json(name = "name") val name: String?,
)