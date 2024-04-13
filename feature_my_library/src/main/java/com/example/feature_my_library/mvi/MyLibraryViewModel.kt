package com.example.feature_my_library.mvi

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.core.mvi.impl.BaseMviViewModel
import com.example.core.mvi.impl.NavigationEvent
import com.example.core_data.database.dao.TitleFileDao
import com.example.core_data.dto.parser.toDto
import com.example.core_domain.model.file.TitleFile
import com.example.feature_my_library.ui.copyTo
import com.example.util.getFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyLibraryViewModel @Inject constructor(
    override val model: MangaLibraryModel,
    private val applicationContext: Context,
    private val titleFileDao: TitleFileDao
) : BaseMviViewModel<MyLibraryAction, MyLibraryEvent, MyLibraryState, NavigationEvent>() {

    val titleFiles = titleFileDao.getTitleFiles()

    override fun onViewEvent(event: MyLibraryEvent) {

    }

    override fun onChangeState(state: MyLibraryState) {

    }

    fun saveFiles(uris: List<Uri>, dir: File) {
        viewModelScope.launch(Dispatchers.IO) {
            val files = buildList {
                uris.forEach {  uri ->
                    add(saveFile(uri, dir))
                }
            }

            titleFileDao.insertTitleFiles(files.map { it.toDto() })
        }
    }

    private fun saveFile(uri: Uri, dir: File): TitleFile {
        val file = uri.getFile(applicationContext)
        val newFile = File("${dir.absolutePath}/${file?.name}")

        file?.copyTo(newFile)

        return TitleFile(
            path = newFile.absolutePath
        )
    }
}
