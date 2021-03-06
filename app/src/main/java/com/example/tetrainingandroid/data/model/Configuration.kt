package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class Configuration(
    @Json(name = "images") val imageConfiguration: ImageConfiguration,
    @Json(name = "change_keys") val changeKeys: List<String>,
)