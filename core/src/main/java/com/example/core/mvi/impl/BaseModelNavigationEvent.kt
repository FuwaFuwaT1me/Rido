package com.example.core.mvi.impl

import com.example.core.mvi.api.ModelNavigationEvent
import com.example.core.mvi.api.MviNavigationEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class BaseModelNavigationEvent<NavEvent : MviNavigationEvent> (
    private val scope: CoroutineScope
) : ModelNavigationEvent<NavEvent> {

    private val _navigationEvent = MutableSharedFlow<NavEvent>(replay = 0)
    override val navigationEvent: Flow<NavEvent>
        get() = _navigationEvent

    override fun sendNavigationEvent(event: NavEvent) {
        scope.launch(Dispatchers.Default) {
            _navigationEvent.emit(event)
        }
    }
}
