package com.example.rido.navigation

import com.example.common.navigation.DataBundle

sealed interface Screen {

    val dataBundle: DataBundle
}

data class MyLibraryScreen(
    override val dataBundle: DataBundle
) : Screen

data class ReaderScreen(
    override val dataBundle: DataBundle
) : Screen