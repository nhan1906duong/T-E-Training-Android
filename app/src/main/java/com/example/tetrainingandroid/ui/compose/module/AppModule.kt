package com.example.tetrainingandroid.ui.compose.module

import com.example.tetrainingandroid.ui.compose.module.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNavigationManager() = NavigationManager()

}