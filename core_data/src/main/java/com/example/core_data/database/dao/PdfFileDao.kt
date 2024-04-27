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

    @Query("SELECT * FROM PdfFileDto WHERE id = :id LIMIT 1")
    suspend fun getPdfFile(id: String): PdfFileDto

    @Insert
    suspend fun insertPdfFile(pdfFile: PdfFileDto)

    @Insert
    suspend fun insertPdfFiles(pdfFiles: List<PdfFileDto>)

    @Query("UPDATE PdfFileDto SET currentPage = :pageNumber WHERE id = :id")
    fun updateLastReadPage(id: String, pageNumber: Int)
}
