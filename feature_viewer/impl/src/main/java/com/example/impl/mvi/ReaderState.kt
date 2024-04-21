package com.example.impl.mvi

import com.example.core.mvi.api.State

data class ReaderState(
    val isLoading: Boolean
) : State