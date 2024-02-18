package com.example.core.mvi.impl

import com.example.core.mvi.api.MviNavigationEvent
import com.example.core.mvi.api.Router

class BaseRouter : Router<MviNavigationEvent> {

    override fun onNavigationEvent(event: MviNavigationEvent) {
        when (event) {
            is NavigationEvent.Navigate -> {

            }
            NavigationEvent.Back -> {

            }
        }
    }
}
