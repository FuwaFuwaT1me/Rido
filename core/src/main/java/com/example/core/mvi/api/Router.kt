package com.example.core.mvi.api

interface Router<NavEvent: MviNavigationEvent> {
    fun onNavigationEvent(event: NavEvent)
}
