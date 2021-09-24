package com.example.tetrainingandroid.data.service

import com.example.tetrainingandroid.data.model.People
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleService {

    @GET("/3/person/{person_id}")
    suspend fun getPeople(
        @Path("person_id") personId: Int,
        @Query("append_to_response") appendToResponse: String? = null
    ): People
}