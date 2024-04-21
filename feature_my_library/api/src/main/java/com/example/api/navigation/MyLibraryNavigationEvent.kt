package com.example.api.navigation

import com.example.core.mvi.impl.BaseNavigationEvent

data class NavigateToReader(
    override val dataBundle: ReaderDataBundle,
    override val route: String = ReaderNavScreen.name,
) : BaseNavigationEvent.NavigateTo