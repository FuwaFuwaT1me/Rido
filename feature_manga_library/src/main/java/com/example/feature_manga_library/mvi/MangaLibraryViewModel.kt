package com.example.feature_manga_library.mvi

import com.example.core.mvi.api.MviNavigationEvent
import com.example.core.mvi.impl.BaseMviViewModel

class MangaLibraryViewModel(
    override val model: MangaLibraryModel,
) : BaseMviViewModel<MangaLibraryAction, MangaLibraryEvent, MangaLibraryState, MviNavigationEvent>() {

    override fun onViewEvent(event: MangaLibraryEvent) {
        TODO("Not yet implemented")
    }

    override fun onChangeState(state: MangaLibraryState) {
        TODO("Not yet implemented")
    }
}
