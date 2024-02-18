package com.example.feature_manga_library.mvi

import com.example.core.mvi.api.MviNavigationEvent
import com.example.core.mvi.impl.BaseMviViewModel
import com.example.core.mvi.impl.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyLibraryViewModel @Inject constructor(
    override val model: MangaLibraryModel,
) : BaseMviViewModel<MyLibraryAction, MyLibraryEvent, MyLibraryState, NavigationEvent>() {

    override fun onViewEvent(event: MyLibraryEvent) {

    }

    override fun onChangeState(state: MyLibraryState) {

    }
}
