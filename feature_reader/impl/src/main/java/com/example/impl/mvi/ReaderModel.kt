package com.example.impl.mvi

import com.example.core.mvi.impl.BaseModel
import com.example.core.mvi.impl.BaseNavigationEvent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ReaderModel @Inject constructor(
    defaultViewState: ReaderState,
    scope: CoroutineScope
) : BaseModel<ReaderState, ReaderAction, BaseNavigationEvent>(
    defaultViewState, scope
) {

    override fun onAction(action: ReaderAction) {
        when (action) {
            ReaderLoaded -> {
                updateState { copy(isLoading = false) }
            }

            ReaderStartLoading -> {
                updateState { copy(isLoading = true) }
            }
        }
    }
}