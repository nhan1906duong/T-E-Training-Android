package com.example.tetrainingandroid.repo

import com.example.tetrainingandroid.data.model.Searchable
import com.example.tetrainingandroid.data.service.SearchService
import com.example.tetrainingandroid.di.DispatchersIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val service: SearchService,
    @DispatchersIO private val dispatchersIO: CoroutineDispatcher
) {
    suspend fun search(query: String): List<Searchable?> {
        val result: List<Searchable?>
        withContext(dispatchersIO) {
            result = (async {service.search(query) }).await().results ?: listOf()
        }
        return result
    }
}