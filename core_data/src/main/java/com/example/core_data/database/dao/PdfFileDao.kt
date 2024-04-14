package com.example.core_data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core_data.dto.PdfFileDto
import kotlinx.coroutines.flow.Flow

@Dao
interface PdfFileDao {

    @Query("SELECT * FROM PdfFileDto")
    fun getPdfFiles(): Flow<List<PdfFileDto>>

    @Insert
    fun insertPdfFile(pdfFiles: PdfFileDto)

    @Insert
    fun insertPdfFiles(pdfFiles: List<PdfFileDto>)
}
