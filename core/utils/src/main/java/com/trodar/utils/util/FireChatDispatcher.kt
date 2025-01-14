package com.trodar.utils.util

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val fireDispatcher: FireDispatchers)

enum class FireDispatchers {
    Default,
    IO,
}