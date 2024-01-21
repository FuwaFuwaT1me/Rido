package com.example.core.mvi.impl

import com.example.core.mvi.api.Router

class BaseRouter : Router<NavigationEvent> {

    override fun onNavigationEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.Navigate -> {

            }
            NavigationEvent.Back -> {

            }
        }
    }
}
