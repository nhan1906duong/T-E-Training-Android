package com.example.tetrainingandroid.repo

import android.util.Log
import com.example.tetrainingandroid.di.DispatchersIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeRepository @Inject constructor(@DispatchersIO private val coroutineDispatcher: CoroutineDispatcher) {
    suspend fun request() {
        withContext(coroutineDispatcher) {
            Log.d("MSG", "Thread name: ${Thread.currentThread().name}")
        }
    }
}