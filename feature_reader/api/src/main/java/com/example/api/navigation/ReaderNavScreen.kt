package com.example.api.navigation

import com.example.common.navigation.Screen
import kotlinx.serialization.Serializable

@Serializable
data class ReaderNavScreen(
    val dataBundle: ReaderDataBundle
) : Screen