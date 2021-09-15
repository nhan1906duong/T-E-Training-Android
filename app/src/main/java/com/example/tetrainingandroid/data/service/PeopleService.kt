package com.example.tetrainingandroid.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleService {

    @GET("/person/{person_id}")
    suspend fun getPeople(
        @Path("person_id") personId: Int,
        @Query("append_to_response") appendToResponse: String? = null
    ): PeopleService
}