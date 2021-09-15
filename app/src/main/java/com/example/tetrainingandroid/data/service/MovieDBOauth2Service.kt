package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.model.RequestTokenResponse
import com.example.tetrainingandroid.data.model.Session
import com.example.tetrainingandroid.data.request.SessionRequestParams
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieDBOauth2Service {
    @GET("/3/authentication/token/new")
    suspend fun requestToken(): RequestTokenResponse

    @POST("/3/authentication/session/new")
    suspend fun createSession(@Body requestToken: SessionRequestParams): Session
}