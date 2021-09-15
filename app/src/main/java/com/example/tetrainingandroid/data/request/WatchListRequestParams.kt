package com.example.tetrainingandroid.data.request

import com.squareup.moshi.Json

data class WatchListRequestParams(
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "media_type") val mediaId: Int,
    @Json(name = "watchlist") val watchlist: Boolean,
) {
    companion object {
        fun addToWatchlist(mediaId: Int): WatchListRequestParams =
            WatchListRequestParams(mediaType = "movie", mediaId = mediaId, watchlist = true)

        fun removeFromWatchlist(mediaId: Int): WatchListRequestParams =
            WatchListRequestParams(mediaType = "movie", mediaId = mediaId, watchlist = false)
    }
}