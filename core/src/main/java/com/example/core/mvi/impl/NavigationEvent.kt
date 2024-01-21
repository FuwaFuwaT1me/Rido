package com.example.core.mvi.impl

sealed interface NavigationEvent {

    class Navigate() : NavigationEvent

    object Back : NavigationEvent
}
