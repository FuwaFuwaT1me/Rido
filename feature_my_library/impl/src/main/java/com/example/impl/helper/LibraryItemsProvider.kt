package com.example.impl.helper

import com.example.core_data.database.dao.ImageFileDao
import com.example.core_data.database.dao.PdfFileDao
import com.example.core_data.dto.parser.toDomain
import com.example.core_domain.model.common.LibraryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class LibraryItemsProvider @Inject constructor(
    private val pdfFileDao: PdfFileDao,
    private val imageFileDao: ImageFileDao,
) {

    fun getTitles(): Flow<List<LibraryItem>> {
        return combine(
            pdfFileDao.getPdfFiles(),
            imageFileDao.getImageFiles(),
        ) { pdfFiles, imageFiles ->
            val combinedList = mutableListOf<LibraryItem>()

            combinedList.addAll(pdfFiles.map { it.toDomain() })
//            combinedList.addAll(imageFiles.map { it.toDomain() })

            combinedList.sort()
        }
    }

    private fun List<LibraryItem>.sort(): List<LibraryItem> {
        return this.sortedBy { it.id }
    }
}