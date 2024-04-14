package com.example.core_data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core_data.dto.ImageFileDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageFileDao {

    @Query("SELECT * FROM ImageFileDto")
    fun getImageFiles(): Flow<List<ImageFileDto>>

    @Insert
    fun insertImageFile(pdfFiles: ImageFileDto)

    @Insert
    fun insertImageFiles(pdfFiles: List<ImageFileDto>)
}
