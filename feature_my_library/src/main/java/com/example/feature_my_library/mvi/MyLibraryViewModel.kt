package com.example.feature_my_library.mvi

import com.example.core.mvi.impl.BaseMviViewModel
import com.example.core.mvi.impl.NavigationEvent
import com.example.core_data.database.dao.TitleFileDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyLibraryViewModel @Inject constructor(
    override val model: MyLibraryModel,
    titleFileDao: TitleFileDao
) : BaseMviViewModel<MyLibraryAction, MyLibraryEvent, MyLibraryState, NavigationEvent>() {

    val titleFiles = titleFileDao.getTitleFiles()

    override fun onViewEvent(event: MyLibraryEvent) {
    }

    override fun onChangeState(state: MyLibraryState) {

    }
}
