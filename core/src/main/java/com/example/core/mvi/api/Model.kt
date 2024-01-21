package com.example.core.mvi.api

import com.example.core.mvi.impl.NavigationEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface Model<ViewState, ViewAction, ViewEvent, NavEvent>
    where ViewState : State,
          ViewAction : Action,
          ViewEvent : Event,
          NavEvent : NavigationEvent {


    val viewState: StateFlow<ViewState>
    val viewEvents: Flow<ViewEvent>
    val navigationEvent: Flow<NavEvent>

    fun onViewAction(action: ViewAction)

    fun clean()
}
