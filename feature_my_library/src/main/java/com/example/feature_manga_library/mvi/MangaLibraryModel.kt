package com.example.feature_manga_library.mvi

import com.example.core.mvi.api.MviNavigationEvent
import com.example.core.mvi.impl.BaseModel
import com.example.core.mvi.impl.NavigationEvent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MangaLibraryModel @Inject constructor(
    defaultViewState: MyLibraryState,
    scope: CoroutineScope,
) : BaseModel<MyLibraryState, MyLibraryAction, MyLibraryEvent, NavigationEvent>(
    defaultViewState, scope
) {

    override fun onViewAction(action: MyLibraryAction) {
        TODO("Not yet implemented")
    }
}
