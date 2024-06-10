package com.example.core.mvi.api

import kotlinx.coroutines.flow.StateFlow

interface State

interface UiStateFlow<UiState : State> {

    val state: StateFlow<UiState>

    fun updateState(updateState: UiState.() -> UiState)
}
