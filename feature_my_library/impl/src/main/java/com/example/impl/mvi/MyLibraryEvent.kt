package com.example.impl.mvi

import com.example.core.mvi.api.Event

sealed interface MyLibraryEvent : Event

data object ShowFilePickerEvent : MyLibraryEvent
