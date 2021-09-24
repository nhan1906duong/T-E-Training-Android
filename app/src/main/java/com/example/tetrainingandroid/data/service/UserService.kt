package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.model.Movie
import com.example.tetrainingandroid.data.request.FavoriteRequestParams
import com.example.tetrainingandroid.data.request.ReviewRequestParams
import com.example.tetrainingandroid.data.request.WatchListRequestParams
import com.example.tetrainingandroid.data.response.PageResponse
import com.example.tetrainingandroid.data.response.PostResponse
import retrofit2.http.*

interface UserService {
    @POST("/3/movie/{movie_id}/rating")
    suspend fun postRating(
        @Path("movie_id") movieId: Int,
        @Body body: ReviewRequestParams
    ): PostResponse

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
