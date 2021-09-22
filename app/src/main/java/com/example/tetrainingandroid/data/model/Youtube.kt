package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json
import java.util.*

data class Youtube(
    @Json(name = "iso_3166_1") val iso3166_1: String?,
    @Json(name = "iso_639_1") val iso639_1: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "key") val key: String?,
    @Json(name = "site") val youtube: String?,
    @Json(name = "size") val size: Int?,
    @Json(name = "type") val type: String?,
    @Json(name = "official") val official: Boolean?,
    @Json(name = "published_at") val publishedAt: Date?,
    @Json(name = "id") val id: String?,
)