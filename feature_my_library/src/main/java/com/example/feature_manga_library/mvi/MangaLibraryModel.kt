package com.example.feature_manga_library.mvi

import com.example.core.mvi.api.MviNavigationEvent
import com.example.core.mvi.impl.BaseModel
import com.example.core.mvi.impl.NavigationEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MangaLibraryModel @Inject constructor(
    defaultViewState: MangaLibraryState,
    scope: CoroutineScope,
) : BaseModel<MangaLibraryState, MangaLibraryAction, MangaLibraryEvent, MviNavigationEvent>(
    defaultViewState, scope
) {

    override fun onViewAction(action: MangaLibraryAction) {
        TODO("Not yet implemented")
    }
}
