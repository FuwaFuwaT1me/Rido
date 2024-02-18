package com.example.core.mvi.impl

import com.example.core.mvi.api.MviNavigationEvent

sealed interface NavigationEvent : MviNavigationEvent {

    class Navigate() : NavigationEvent

    object Back : NavigationEvent
}
