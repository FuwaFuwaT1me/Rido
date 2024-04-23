package com.example.impl.mvi

import com.example.core.mvi.impl.BaseNavigationEvent
import com.example.core.mvi.impl.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyLibraryViewModel @Inject constructor(
    override val model: MyLibraryModel,
) : BaseViewModel<MyLibraryAction, MyLibraryEvent, MyLibraryState, BaseNavigationEvent>()
