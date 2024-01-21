package com.example.core.mvi.impl

import com.example.core.mvi.api.Event
import com.example.core.mvi.api.Events
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class BaseEvents<ViewEvent : Event>(
    private val scope: CoroutineScope
) : Events<ViewEvent> {

    private val _viewEvents = MutableSharedFlow<ViewEvent>()

    override val viewEvents: Flow<ViewEvent>
        get() = _viewEvents

    override fun sendViewEvent(event: ViewEvent) {
        scope.launch(Dispatchers.Default) {
            _viewEvents.emit(event)
        }
    }
}
