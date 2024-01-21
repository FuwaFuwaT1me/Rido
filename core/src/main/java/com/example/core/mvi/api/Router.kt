package com.example.core.mvi.api

import com.example.core.mvi.impl.NavigationEvent

interface Router<NavEvent: NavigationEvent> {
    fun onNavigationEvent(event: NavEvent)
}
