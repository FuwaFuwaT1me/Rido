package com.example.core.mvi.impl

import androidx.navigation.NamedNavArgument
import com.example.core.mvi.api.MviNavigationEvent

sealed interface NavigationEvent : MviNavigationEvent {

    class Navigate(
        route: String,
        args: List<NamedNavArgument>
    ) : NavigationEvent

    object Back : NavigationEvent
}
