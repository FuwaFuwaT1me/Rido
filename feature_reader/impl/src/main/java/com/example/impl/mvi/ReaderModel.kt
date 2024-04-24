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

    override fun onViewAction(action: ReaderAction) {

    }
}