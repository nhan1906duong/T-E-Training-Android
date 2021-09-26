package com.example.tetrainingandroid.data.request

import com.squareup.moshi.Json

data class FavoriteRequestParams(
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "media_id") val mediaId: Int,
    @Json(name = "favorite") val favorite: Boolean,
) {
    companion object {
        fun markFavorite(mediaId: Int): FavoriteRequestParams =
            FavoriteRequestParams(mediaType = "movie", mediaId = mediaId, favorite = true)

        fun removeFavorite(mediaId: Int): FavoriteRequestParams =
            FavoriteRequestParams(mediaType = "movie", mediaId = mediaId, favorite = false)
    }
}