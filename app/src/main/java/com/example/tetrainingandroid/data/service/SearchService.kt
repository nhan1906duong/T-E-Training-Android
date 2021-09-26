package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.model.Searchable
import com.example.tetrainingandroid.data.response.PageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/3/search/multi")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): PageResponse<Searchable?>
}