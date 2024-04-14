package com.example.core.mvi.impl

import com.example.core.mvi.api.Action
import com.example.core.mvi.api.Actions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class BaseActions<ViewAction : Action>(
    private val scope: CoroutineScope
) : Actions<ViewAction> {

    private val _viewActions = MutableSharedFlow<ViewAction>()

    override val viewActions: Flow<ViewAction>
        get() = _viewActions

    override fun sendViewAction(action: ViewAction) {
        scope.launch(Dispatchers.Default) {
            _viewActions.emit(action)
        }
    }
}
