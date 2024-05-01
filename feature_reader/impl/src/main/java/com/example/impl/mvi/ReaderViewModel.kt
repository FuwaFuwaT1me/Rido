package com.example.impl.mvi

import androidx.lifecycle.viewModelScope
import com.example.core.mvi.impl.BaseNavigationEvent
import com.example.core.mvi.impl.BaseViewModel
import com.example.core_data.database.dao.ImageFileDao
import com.example.core_data.database.dao.PdfFileDao
import com.example.core_data.dto.parser.toDomain
import com.example.core_domain.model.common.LibraryItem
import com.example.core_domain.model.common.LibraryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    override val model: ReaderModel,
    private val pdfFileDao: PdfFileDao,
    private val imageFileDao: ImageFileDao,
) : BaseViewModel<ReaderAction, ReaderState, BaseNavigationEvent>() {

    suspend fun getLibraryItem(
        id: String,
        libraryType: LibraryType
    ): LibraryItem? {
        return withContext(Dispatchers.IO) {
            // TODO : process if file is not found
            when (libraryType) {
                LibraryType.PDF -> {
                    pdfFileDao.getPdfFile(id).toDomain()
                }
                LibraryType.IMAGE -> {
                    imageFileDao.getImageFile(id).toDomain()
                }

                else -> null
            }
        }
    }

    fun updateLastReadPage(id: String, pageNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            pdfFileDao.updateLastReadPage(id, pageNumber)
        }
    }
}