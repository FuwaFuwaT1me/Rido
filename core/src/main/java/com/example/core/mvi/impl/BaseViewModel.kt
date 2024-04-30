package com.example.core.mvi.impl

import androidx.lifecycle.ViewModel
import com.example.core.mvi.api.Action
import com.example.core.mvi.api.Actions
import com.example.core.mvi.api.Model
import com.example.core.mvi.api.MviNavigationEvent
import com.example.core.mvi.api.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel<ViewAction, ViewState, NavEvent> : ViewModel()
        where ViewAction : Action,
              ViewState : State,
              NavEvent : MviNavigationEvent
{

    abstract val model: Model<ViewState, ViewAction, NavEvent>

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private val actions: Actions<ViewAction> = BaseActions(scope)

    private val viewActions: Flow<ViewAction> = actions.viewActions

    fun init() {
        scope.coroutineContext.cancelChildren()
        bindFlows()
    }

    fun sendAction(action: ViewAction) {
        scope.launch {
            actions.sendViewAction(action)
        }
    }

    fun sendNavigationEvent(navEvent: NavEvent) {
        model.sendNavigationEvent(navEvent)
    }

    private fun bindFlows() {
        scope.launch {
            viewActions.collect { viewAction ->
                model.onViewAction(viewAction)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        //TODO: cancel() or cancelChildren()
        scope.cancel()
    }
}
