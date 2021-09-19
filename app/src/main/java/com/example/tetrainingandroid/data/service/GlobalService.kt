package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.model.Configuration
import retrofit2.http.GET

interface GlobalService {
    @GET("/3/configuration")
    suspend fun getConfiguration(): Configuration
}