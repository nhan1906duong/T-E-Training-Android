package com.example.tetrainingandroid.repo

import com.example.tetrainingandroid.data.service.PeopleService
import com.example.tetrainingandroid.di.DispatchersIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(
    private val service: PeopleService,
    @DispatchersIO private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend fun getPeople(peopleId: Int) = withContext(coroutineDispatcher) {
        service.getPeople(peopleId, "images,movie_credits,combined_credits")
    }
}