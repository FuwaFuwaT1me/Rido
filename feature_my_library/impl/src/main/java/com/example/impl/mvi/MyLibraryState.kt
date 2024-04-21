package com.example.impl.mvi

import com.example.core.mvi.api.State
import com.example.core_domain.model.common.LibraryItem

data class MyLibraryState(
    val libraryItems: List<LibraryItem>,
    val showFilePicker: Boolean,
) : State
