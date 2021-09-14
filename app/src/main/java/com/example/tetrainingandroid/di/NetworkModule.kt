package com.example.tetrainingandroid.di

import com.example.tetrainingandroid.config.Config
import com.example.tetrainingandroid.data.adapter.DateTimeAdapter
import com.example.tetrainingandroid.data.interceptor.AuthenticationInterceptor
import com.example.tetrainingandroid.data.interceptor.ExtraDataInterceptor
import com.example.tetrainingandroid.data.service.TestService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, DateTimeAdapter())
            .build()
    }

    @Singleton
    @Provides
    fun providerHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun provideClient(
        authenticationInterceptor: AuthenticationInterceptor,
        extraDataInterceptor: ExtraDataInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authenticationInterceptor)
            .addInterceptor(extraDataInterceptor)
            .addInterceptor(httpLoggingInterceptor)
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

    @Singleton
    @Provides
    fun provideTestService(retrofit: Retrofit): TestService =
        retrofit.create(TestService::class.java)
}