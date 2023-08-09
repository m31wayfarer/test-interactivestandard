package com.review.interactivestandard.utils

import kotlinx.coroutines.CoroutineDispatcher

class AppCoroutineDispatchers(
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val mainImmediate: CoroutineDispatcher
)