package com.example.impl.mvi

import com.example.core.mvi.api.Action

sealed interface ReaderAction : Action

data object ReaderStartLoading : ReaderAction

data object ReaderLoaded : ReaderAction
