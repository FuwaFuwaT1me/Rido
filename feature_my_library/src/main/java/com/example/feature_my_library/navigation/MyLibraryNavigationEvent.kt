package com.example.feature_my_library.navigation

import com.example.core.mvi.impl.NavigationEvent

data class NavigateToReader(
    val fileName: String
) : NavigationEvent