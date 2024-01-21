package com.example.core.mvi.api

import kotlinx.coroutines.flow.Flow

interface Events<ViewEvent : Event> {

    val viewEvents: Flow<ViewEvent>

    fun sendViewEvent(event: ViewEvent)
}
