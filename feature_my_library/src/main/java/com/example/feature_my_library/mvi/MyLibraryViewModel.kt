package com.example.feature_my_library.mvi

import com.example.core.mvi.impl.BaseMviViewModel
import com.example.core.mvi.impl.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyLibraryViewModel @Inject constructor(
    override val model: MyLibraryModel,
) : BaseMviViewModel<MyLibraryAction, MyLibraryEvent, MyLibraryState, NavigationEvent>()
