package com.example.feature_viewer.mvi

import com.example.core.mvi.api.State

data class ReaderState(
    val isLoading: Boolean
) : State