package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.request.FavoriteRequestParams
import com.example.tetrainingandroid.data.request.WatchListRequestParams
import com.example.tetrainingandroid.data.response.PageResponse
import com.example.tetrainingandroid.data.response.PostResponse
import retrofit2.http.*

interface UserService {
    @POST("movie/{movie_id}/rating")
    suspend fun postRating(
        @Query("guest_session_id") guestSessionId: String? = null,
        @Query("session_id") sessionId: String? = null,
        @Body body: Map<String, Any>
    )

    @POST("/account/{account_id}/favorite")
    suspend fun changeFavorite(
        @Path("account_id") accountId: String,
        @Query("session_id") sessionId: String,
        @Body body: FavoriteRequestParams
    ): PostResponse

    @POST("/account/{account_id}/watchlist")
    suspend fun changeAddToWatchList(
        @Path("account_id") accountId: String,
        @Query("session_id") sessionId: String,
        @Body body: WatchListRequestParams
    ): PostResponse

    @GET("/account/{account_id}/watchlist/movies")
    suspend fun getWatchList(
        @Query("session_id") sessionId: String,
        @Query("page") page: String,
    ): PageResponse<Movie>

    @GET("/account/{account_id}/favorite/movies")
    suspend fun getFavorite(
        @Query("session_id") sessionId: String,
        @Query("page") page: String,
    ): PageResponse<Movie>
}
