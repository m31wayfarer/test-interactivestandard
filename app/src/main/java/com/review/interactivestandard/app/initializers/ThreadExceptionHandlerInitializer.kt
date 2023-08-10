package com.review.interactivestandard.app.initializers

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber
import java.io.IOException
import kotlin.system.exitProcess

class ThreadExceptionHandlerInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Timber.i("ThreadExceptionHandlerInitializer create")

        val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        val customExceptionHandler = DefaultExceptionHandler(defaultExceptionHandler)
        Thread.setDefaultUncaughtExceptionHandler(customExceptionHandler)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(TimberInitializer::class.java, DependencyGraphInitializer::class.java)

    class DefaultExceptionHandler(
        private val defaultExceptionHandler: Thread.UncaughtExceptionHandler?
    ) :
        Thread.UncaughtExceptionHandler {
        override fun uncaughtException(thread: Thread, ex: Throwable) {
            try {
                Timber.e(this.javaClass.name, "global exception $thread", ex)
                defaultExceptionHandler?.uncaughtException(thread, ex)
                exitProcess(0)
            } catch (e: IOException) {
                // just catch
            }

        }
    }
}