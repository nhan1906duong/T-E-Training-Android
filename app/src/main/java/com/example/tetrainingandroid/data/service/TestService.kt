package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.model.RequestTokenResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST

interface TestService {
    @GET("/3/authentication/token/new")
    suspend fun getMovies(): RequestTokenResponse
}