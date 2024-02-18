package com.example.core_domain.model.common

sealed interface Source<T> {

    val resource: T

    class Local(
        override val resource: ByteArray,
    ) : Source<ByteArray>

    class Remote(
        override val resource: String,
    ) : Source<String>
}
