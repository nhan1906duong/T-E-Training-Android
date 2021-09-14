package com.example.tetrainingandroid.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
annotation class DispatchersIO

@Qualifier
annotation class DispatchersMain

@InstallIn(SingletonComponent::class)
@Module
object CoroutineModule {

    @DispatchersIO
    @Provides
    fun provideDispatchersIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @DispatchersMain
    @Provides
    fun provideDispatchersMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}