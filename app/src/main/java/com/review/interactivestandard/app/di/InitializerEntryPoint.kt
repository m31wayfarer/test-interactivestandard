package com.review.interactivestandard.app.di

import android.content.Context
import com.review.interactivestandard.app.initializers.ThreadExceptionHandlerInitializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface InitializerEntryPoint {
    companion object {
        //a helper method to resolve the InitializerEntryPoint from the context
        fun resolve(context: Context): InitializerEntryPoint {
            val appContext = context.applicationContext ?: throw IllegalStateException()
            return EntryPointAccessors.fromApplication(
                appContext,
                InitializerEntryPoint::class.java
            )
        }
    }

    fun inject(initializer: ThreadExceptionHandlerInitializer)
}