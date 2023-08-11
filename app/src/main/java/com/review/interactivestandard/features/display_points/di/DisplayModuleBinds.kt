package com.review.interactivestandard.features.display_points.di

import com.review.interactivestandard.features.display_points.data.ImageRepo
import com.review.interactivestandard.features.display_points.domain.IImageInteractor
import com.review.interactivestandard.features.display_points.domain.IImageRepo
import com.review.interactivestandard.features.display_points.domain.ImageInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DisplayModuleBinds {
    @Binds
    fun bindsImageRepo(impl: ImageRepo): IImageRepo

    @Binds
    fun bindsImageInteractor(impl: ImageInteractor): IImageInteractor
}