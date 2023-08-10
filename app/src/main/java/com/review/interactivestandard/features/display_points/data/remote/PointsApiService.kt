package com.review.interactivestandard.features.display_points.data.remote

import com.review.interactivestandard.features.display_points.data.remote.dto.GetPointsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsApiService {
    @GET("/api/test/points")
    suspend fun getPoints(@Query("count") count: Int): GetPointsResponse
}