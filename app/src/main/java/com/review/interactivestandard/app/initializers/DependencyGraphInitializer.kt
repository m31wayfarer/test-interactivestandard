package com.review.interactivestandard.app.initializers

import android.content.Context
import androidx.startup.Initializer
import com.review.interactivestandard.app.di.InitializerEntryPoint
import timber.log.Timber

class DependencyGraphInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        Timber.d("DependencyGraphInitializer create")
        //this will lazily initialize ApplicationComponent before Application's `onCreate`
        InitializerEntryPoint.resolve(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(TimberInitializer::class.java)
    }
}