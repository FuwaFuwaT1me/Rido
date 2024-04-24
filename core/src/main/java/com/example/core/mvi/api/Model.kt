package com.example.core.mvi.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Model<ViewState, ViewAction, NavEvent>
    where ViewState : State,
          ViewAction : Action,
          NavEvent : MviNavigationEvent {


    val viewState: StateFlow<ViewState>
    val navigationEvent: Flow<NavEvent>

    fun onViewAction(action: ViewAction)

    fun sendNavigationEvent(navEvent: NavEvent)

    fun clean()
}
