package com.example.api.navigation

import com.example.common.navigation.Screen
import com.example.core.mvi.impl.BaseNavigationEvent

data class NavigateToReader(
    override val dataBundle: ReaderDataBundle,
    override val screen: Screen = ReaderNavScreen(dataBundle),
) : BaseNavigationEvent.NavigateTo