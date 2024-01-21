package com.example.core.mvi.api

import kotlinx.coroutines.flow.Flow

interface Actions<ViewAction : Action> {

    val viewActions: Flow<ViewAction>

    fun sendViewAction(action: ViewAction)
}
