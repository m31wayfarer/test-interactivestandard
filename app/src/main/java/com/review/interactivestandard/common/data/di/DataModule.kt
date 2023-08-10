package com.review.interactivestandard.common.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    companion object {
        private const val CONNECTION_TIMEOUT: Long = 10 //in seconds
        private const val READ_TIMEOUT: Long = 10 //in seconds
        private const val WRITE_TIMEOUT: Long = 10 //in seconds

        fun builderOkHttpClient(): OkHttpClient.Builder =
            OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    }

    @Provides
    @Singleton
    fun provideOkHttpClientForApi(): OkHttpClient {
        return builderOkHttpClient()
            .build()
    }

    @Provides
    fun provideJson() = Json { ignoreUnknownKeys = true }
}