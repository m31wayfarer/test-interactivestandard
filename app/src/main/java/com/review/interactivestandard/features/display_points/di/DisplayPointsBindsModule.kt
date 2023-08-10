package com.review.interactivestandard.features.display_points.di

import com.review.interactivestandard.features.display_points.data.PointsRepo
import com.review.interactivestandard.features.display_points.data.remote.IPointsRemoteSource
import com.review.interactivestandard.features.display_points.data.remote.PointsRemoteSource
import com.review.interactivestandard.features.display_points.domain.IPointsInteractor
import com.review.interactivestandard.features.display_points.domain.IPointsRepo
import com.review.interactivestandard.features.display_points.domain.PointsInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DisplayPointsBindsModule {
    @Binds
    fun bindsPointsInteractor(impl: PointsInteractor): IPointsInteractor

    @Binds
    fun bindsPointsRepo(impl: PointsRepo): IPointsRepo

    @Binds
    fun bindsPointsRemoteSource(impl: PointsRemoteSource): IPointsRemoteSource
}