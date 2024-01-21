package com.example.core.mvi.impl

import androidx.lifecycle.ViewModel
import com.example.core.mvi.api.Action
import com.example.core.mvi.api.Actions
import com.example.core.mvi.api.Event
import com.example.core.mvi.api.Model
import com.example.core.mvi.api.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<ViewAction, ViewEvent, ViewState, NavEvent>(
    private val model: Model<ViewState, ViewAction, ViewEvent, NavEvent>,
) : ViewModel()
        where ViewAction : Action,
              ViewEvent : Event,
              ViewState : State,
              NavEvent : NavigationEvent
{
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private val router: BaseRouter = BaseRouter()
    private val actions: Actions<ViewAction> = BaseActions(scope)

    val viewActions: Flow<ViewAction>
        get() = actions.viewActions

    abstract fun onViewEvent(event: ViewEvent)
    abstract fun onChangeState(state: ViewState)

    fun init() {
        bindFlows()
    }

    fun sendAction(action: ViewAction) {
        scope.launch {
            actions.sendViewAction(action)
        }
    }

    private fun bindFlows() {
        scope.launch {
            model.viewEvents.collect { viewEvent ->
                onViewEvent(viewEvent)
            }
        }

        scope.launch {
            model.viewState.collect { viewState ->
                onChangeState(viewState)
            }
        }

        scope.launch {
            model.navigationEvent.collect { navEvent ->
                router.onNavigationEvent(navEvent)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        //TODO: cancel() or cancelChildren()
        scope.cancel()
    }
}
