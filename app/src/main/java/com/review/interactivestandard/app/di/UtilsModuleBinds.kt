package com.review.interactivestandard.app.di

import com.review.interactivestandard.utils.IResourceHelper
import com.review.interactivestandard.utils.ResourceHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UtilsModuleBinds {
    @Binds
    fun bindsResourceHelper(impl: ResourceHelper): IResourceHelper
}