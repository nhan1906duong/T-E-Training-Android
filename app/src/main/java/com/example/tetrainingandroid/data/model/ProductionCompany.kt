package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class ProductionCompany(
    @Json(name = "name") val name: String?,
    @Json(name = "id") val id: Int?,
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "origin_country") val originCountry: String?,
)