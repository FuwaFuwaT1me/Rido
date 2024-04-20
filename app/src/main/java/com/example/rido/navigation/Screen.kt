package com.example.rido.navigation

sealed interface Screen {
    val name: String

    data object MyLibraryScreen : Screen { override val name: String = "my_library_screen" }
    data object ReaderScreen : Screen { override val name: String = "reader_screen" }
}
