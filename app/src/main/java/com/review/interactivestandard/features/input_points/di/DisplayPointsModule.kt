package com.review.interactivestandard.features.input_points.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.review.interactivestandard.BuildConfig
import com.review.interactivestandard.features.input_points.data.remote.PointsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class DisplayPointsModule {
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideAuthApiService(retrofit: Retrofit): PointsApiService {
        return retrofit.create(PointsApiService::class.java)
    }
}