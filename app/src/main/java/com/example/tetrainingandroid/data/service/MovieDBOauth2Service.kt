package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.response.RequestTokenResponse
import com.example.tetrainingandroid.data.model.Session
import com.example.tetrainingandroid.data.request.SessionRequestParams
import com.example.tetrainingandroid.data.request.ValidateRequestTokenParams
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieDBOauth2Service {
    @GET("/3/authentication/token/new")
    suspend fun requestToken(): RequestTokenResponse

    @POST("/3/authentication/token/validate_with_login")
    suspend fun validateRequestToken(@Body params: ValidateRequestTokenParams): RequestTokenResponse

    @POST("/3/authentication/session/new")
    suspend fun createSession(@Body params: SessionRequestParams): Session
}