package com.example.core.mvi.api

import com.example.core.mvi.impl.NavigationEvent
import kotlinx.coroutines.flow.Flow

interface ModelNavigationEvent<NavEvent : MviNavigationEvent> {

    val navigationEvent: Flow<NavEvent>

    fun sendNavigationEvent(event: NavEvent)
}
