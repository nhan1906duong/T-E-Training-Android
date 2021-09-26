package com.example.tetrainingandroid.repo

import com.example.tetrainingandroid.data.model.Searchable
import com.example.tetrainingandroid.data.response.PageResponse
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
    suspend fun search(query: String, page: Int): PageResponse<Searchable?> {
        val result: PageResponse<Searchable?>
        withContext(dispatchersIO) {
            result = (async {service.search(query, page) }).await()
        }
        return result
    }
}