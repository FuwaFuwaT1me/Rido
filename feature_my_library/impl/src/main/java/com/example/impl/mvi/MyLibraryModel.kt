package com.example.impl.mvi

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.core.mvi.impl.BaseModel
import com.example.core.mvi.impl.BaseNavigationEvent
import com.example.core_data.database.dao.ImageFileDao
import com.example.core_data.database.dao.PdfFileDao
import com.example.core_data.dto.parser.toDto
import com.example.core_domain.model.common.FileData
import com.example.core_domain.model.justfile.image.ImageFile
import com.example.core_domain.model.justfile.pdf.PdfFile
import com.example.impl.helper.LibraryItemsProvider
import com.example.impl.ui.copyTo
import com.example.util.FileType
import com.example.util.IMAGES_TYPES
import com.example.util.ext
import com.example.util.getFile
import com.example.util.saveAsPngToFile
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
    private val imageFileDao: ImageFileDao,
) : BaseModel<MyLibraryState, MyLibraryAction, BaseNavigationEvent>(
    defaultViewState, scope
) {

    init {
        collectUpdatingLibraryItems()
    }

    override fun onAction(action: MyLibraryAction) {
        when (action) {
            is SaveFilesAction -> {
                scope.launch(Dispatchers.IO) {
                    saveFiles(action.uris)
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

    // TODO : move all this logic to ```interface FileSaver```
    private suspend fun saveFiles(uris: List<Uri>) {
        uris.forEach {  uri ->
            val file = uri.getFile(applicationContext)

            file?.let {
                when (file.ext()) {
                    in IMAGES_TYPES -> saveImageFile(file)
                    FileType.PDF.ext -> savePdfFile(file)
                }
            }
        }
    }

    private suspend fun saveImageFile(file: File) {
        val dir = applicationContext.filesDir
        val stream = file.inputStream()
        val bitmap = BitmapFactory.decodeStream(stream)
        val newFile = File("${dir.absoluteFile}/${file.name}.${file.ext()}")

        bitmap.saveAsPngToFile(newFile)

        imageFileDao.insertImageFile(ImageFile(
            id = (0..999999).random().toString(),
            title = file.name,
            file = FileData(
                path = newFile.absolutePath,
                coverPath = newFile.absolutePath
            )
        ).toDto())
    }

    private suspend fun savePdfFile(file: File) {
        val dir = applicationContext.filesDir
        val pdfFile = savePdfFile(file, dir)

        pdfFile?.let { pdfFileDao.insertPdfFile(it.toDto()) }

        onAction(CloseFilePickerAction)
    }

    private suspend fun savePdfFile(file: File, dir: File): PdfFile? {
        val newFile = File("${dir.absolutePath}/${file.name}")
        val coverFile = File("${dir.absoluteFile}/cover_${file.name}.png")

        file.copyTo(newFile)

        val pageCount = newFile.savePdfFirstFrameToFile(coverFile) ?: return null

        return runCatching {
            PdfFile(
                id = (0..999999).random().toString(),
                title = file.name ?: "",
                file = FileData(
                    path = newFile.absolutePath,
                    coverPath = coverFile.absolutePath
                ),
                pageCount = pageCount,
                currentPage = 0
            )
        }.getOrNull()
    }
}
