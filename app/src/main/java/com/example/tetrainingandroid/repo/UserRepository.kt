package com.example.tetrainingandroid.repo

import com.example.tetrainingandroid.data.service.UserService
import com.example.tetrainingandroid.di.DispatchersIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val service: UserService,
    @DispatchersIO private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend fun getUser() = withContext(coroutineDispatcher) { service.getUser() }

}