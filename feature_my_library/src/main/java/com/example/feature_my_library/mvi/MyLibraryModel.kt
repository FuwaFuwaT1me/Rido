package com.example.feature_my_library.mvi

import android.content.Context
import android.net.Uri
import com.example.core.mvi.impl.BaseModel
import com.example.core.mvi.impl.NavigationEvent
import com.example.core_data.database.dao.TitleFileDao
import com.example.core_data.dto.parser.toDto
import com.example.core_domain.model.file.TitleFile
import com.example.feature_my_library.ui.copyTo
import com.example.util.getFile
import com.example.util.savePdfFirstFrameToFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class MyLibraryModel @Inject constructor(
    defaultViewState: MyLibraryState,
    scope: CoroutineScope,
    private val applicationContext: Context,
    private val titleFileDao: TitleFileDao,
) : BaseModel<MyLibraryState, MyLibraryAction, MyLibraryEvent, NavigationEvent>(
    defaultViewState, scope
) {

    override fun onViewAction(action: MyLibraryAction) {
        when (action) {
            is SaveFilesAction -> {
                saveFiles(action.uris)
            }
        }
    }

    private fun saveFiles(uris: List<Uri>) {
        scope.launch(Dispatchers.IO) {
            val dir = applicationContext.filesDir
            val files = buildList {
                uris.forEach {  uri ->
                    add(saveFile(uri, dir))
                }
            }

            titleFileDao.insertTitleFiles(files.map { it.toDto() })
        }
    }

    private suspend fun saveFile(uri: Uri, dir: File): TitleFile {
        val file = uri.getFile(applicationContext)
        val newFile = File("${dir.absolutePath}/${file?.name}")
        val coverFile = File("${dir.absoluteFile}/cover_${file?.name}.png")

        file?.copyTo(newFile)

        newFile.savePdfFirstFrameToFile(coverFile)

        return TitleFile(
            path = newFile.absolutePath,
            coverPath = coverFile.absolutePath
        )
    }
}
