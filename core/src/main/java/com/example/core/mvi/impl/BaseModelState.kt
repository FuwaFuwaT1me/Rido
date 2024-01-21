package com.example.core.mvi.impl

import com.example.core.mvi.api.ModelState
import com.example.core.mvi.api.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BaseModelState<ViewState : State>(
    defaultViewState: ViewState,
    private val scope: CoroutineScope
) : ModelState<ViewState> {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val updateStateDispatcher = Dispatchers.Default.limitedParallelism(1)

    private val _viewState = MutableStateFlow(defaultViewState)

    override val viewState: StateFlow<ViewState>
        get() = _viewState

    override fun updateState(updateViewState: ViewState.() -> ViewState) {
        scope.launch(updateStateDispatcher) {
            val newState = updateViewState(_viewState.value)
            _viewState.value = newState
        }
    }
}
