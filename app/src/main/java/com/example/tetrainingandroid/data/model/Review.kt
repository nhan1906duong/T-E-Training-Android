package com.example.tetrainingandroid.data.model

import com.squareup.moshi.Json
import java.util.*

data class Review(
    @Json(name = "author") val authorUsername: String?,
    @Json(name = "author_details") val author: Author?,
    @Json(name = "content") val content: String?,
    @Json(name = "created_at") val createdAt: Date?,
    @Json(name = "id") val id: String?,
    @Json(name = "updated_at") val updatedAt: Date?,
    @Json(name = "url") val url: String?,
)