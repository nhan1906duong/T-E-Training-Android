package com.example.tetrainingandroid.di

import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.interceptor.AuthenticationInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory::class)
            .build()
    }

    @Singleton
    @Provides
    fun provideClient(interceptor: AuthenticationInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(Config.READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(Config.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Config.BASE_URL)
            .build()
    }
}