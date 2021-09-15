package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.model.ReviewResponse
import retrofit2.http.*

interface ReviewService {
    @GET("/movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1
    ): ReviewResponse

}