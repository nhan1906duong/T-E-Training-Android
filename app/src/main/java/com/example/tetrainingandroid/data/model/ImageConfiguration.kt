package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json

data class ImageConfiguration(
    @Json(name = "base_url") val baseUrl: String,
    @Json(name = "secure_base_url") val secureBaseUrl: String,
    @Json(name = "backdrop_sizes") val backdropSizes: List<String>,
    @Json(name = "logo_sizes") val logoSizes: List<String>,
    @Json(name = "poster_sizes") val posterSizes: List<String>,
) {
    enum class PosterSize(val size: String) {
        REGULAR("w185"),
        MEDIUM("w342"),
        LARGE("w500"),
        ORIGINAL("original")
    }
}