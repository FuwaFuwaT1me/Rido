package com.example.feature_manga_library.mvi

import com.example.core.mvi.api.MviNavigationEvent
import com.example.core.mvi.impl.BaseMviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyLibraryViewModel @Inject constructor(
    override val model: MangaLibraryModel,
) : BaseMviViewModel<MangaLibraryAction, MangaLibraryEvent, MangaLibraryState, MviNavigationEvent>() {

    override fun onViewEvent(event: MangaLibraryEvent) {

    }

    override fun onChangeState(state: MangaLibraryState) {

    }
}
