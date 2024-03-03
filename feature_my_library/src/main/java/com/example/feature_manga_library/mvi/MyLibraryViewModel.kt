package com.example.feature_manga_library.mvi

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.impl.BaseMviViewModel
import com.example.core.mvi.impl.NavigationEvent
import com.example.core_data.database.dao.TitleFileDao
import com.example.core_data.dto.parser.toDto
import com.example.core_domain.model.file.TitleFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyLibraryViewModel @Inject constructor(
    override val model: MangaLibraryModel,
    private val titleFileDao: TitleFileDao
) : BaseMviViewModel<MyLibraryAction, MyLibraryEvent, MyLibraryState, NavigationEvent>() {

    val titleFiles = titleFileDao.getTitleFiles()

    override fun onViewEvent(event: MyLibraryEvent) {

    }

    override fun onChangeState(state: MyLibraryState) {

    }

    fun saveFiles(titleFiles: List<TitleFile>) {
        viewModelScope.launch(Dispatchers.IO) {
            titleFileDao.insertTitleFiles(titleFiles.map { it.toDto() })
        }
    }
}
