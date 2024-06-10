package com.example.core.mvi.impl

import com.example.core.mvi.api.Action
import com.example.core.mvi.api.ActionFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class BaseActionFlow<UiAction : Action>(
    private val scope: CoroutineScope
) : ActionFlow<UiAction> {

    private val _uiActions = MutableSharedFlow<UiAction>()

    override val actions: Flow<UiAction>
        get() = _uiActions

    override fun sendAction(action: UiAction) {
        scope.launch(Dispatchers.Default) {
            _uiActions.emit(action)
        }
    }
}
