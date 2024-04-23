package com.example.impl.mvi

import android.content.Context
import android.net.Uri
import com.example.core.mvi.impl.BaseModel
import com.example.core.mvi.impl.BaseNavigationEvent
import com.example.core_data.database.dao.PdfFileDao
import com.example.core_data.dto.parser.toDto
import com.example.core_domain.model.common.FileData
import com.example.core_domain.model.justfile.pdf.PdfFile
import com.example.impl.helper.LibraryItemsProvider
import com.example.impl.ui.copyTo
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
    private val libraryItemsProvider: LibraryItemsProvider,
    private val pdfFileDao: PdfFileDao,
) : BaseModel<MyLibraryState, MyLibraryAction, MyLibraryEvent, BaseNavigationEvent>(
    defaultViewState, scope
) {

    init {
        collectUpdatingLibraryItems()
    }

    override fun onViewAction(action: MyLibraryAction) {
        when (action) {
            is SaveFilesAction -> {
                scope.launch(Dispatchers.IO) {
                    savePdfFiles(action.uris)
                }
            }
            OpenFilePickerAction -> { updateState { copy(showFilePicker = true) } }
            CloseFilePickerAction -> { updateState { copy(showFilePicker = false) } }
        }
    }

    private fun collectUpdatingLibraryItems() {
        scope.launch(Dispatchers.IO) {
            libraryItemsProvider.getTitles().collect {
                updateState {
                    copy(libraryItems = it)
                }
            }
        }
    }

    private suspend fun savePdfFiles(uris: List<Uri>) {
        val dir = applicationContext.filesDir
        val files = buildList {
            uris.forEach {  uri ->
                add(savePdfFile(uri, dir))
            }
        }

        pdfFileDao.insertPdfFiles(files.map { it.toDto() })

        onViewAction(CloseFilePickerAction)
    }

    private suspend fun savePdfFile(uri: Uri, dir: File): PdfFile {
        val file = uri.getFile(applicationContext)
        val newFile = File("${dir.absolutePath}/${file?.name}")
        val coverFile = File("${dir.absoluteFile}/cover_${file?.name}.png")

        file?.copyTo(newFile)

        newFile.savePdfFirstFrameToFile(coverFile)

        return PdfFile(
            id = (0..999999).random().toString(),
            title = file?.name ?: "",
            file = FileData(
                path = newFile.absolutePath,
                coverPath = coverFile.absolutePath
            )
        )
    }
}
