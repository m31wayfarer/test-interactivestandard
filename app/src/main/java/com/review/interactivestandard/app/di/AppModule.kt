package com.review.interactivestandard.app.di

import com.review.interactivestandard.utils.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {
    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
        mainImmediate = Dispatchers.Main.immediate
    )

    @Singleton
    @Provides
    @AppScope
    fun provideAppScope(dispatchers: AppCoroutineDispatchers): CoroutineScope = CoroutineScope(
        SupervisorJob() + dispatchers.computation)
}

@Qualifier
annotation class AppScope