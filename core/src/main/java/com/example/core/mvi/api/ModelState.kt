package com.example.core.mvi.api

import kotlinx.coroutines.flow.StateFlow

interface ModelState<ViewState : State> {

    val viewState: StateFlow<ViewState>

    fun updateState(updateViewState: ViewState.() -> ViewState)
}

fun <ViewState : State> ModelState<ViewState>.withState(withViewState: ViewState.() -> Unit) {
    updateState {
        withViewState(this)
        this
    }
}
