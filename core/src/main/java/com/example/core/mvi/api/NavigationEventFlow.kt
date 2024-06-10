package com.example.core.mvi.api

import kotlinx.coroutines.flow.Flow

interface NavigationEvent

interface NavigationEventFlow<NavEvent : NavigationEvent> {

    val navigationEvent: Flow<NavEvent>

    fun sendNavigationEvent(event: NavEvent)
}
