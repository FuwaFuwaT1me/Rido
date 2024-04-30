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
    suspend fun insertImageFile(imageFile: ImageFileDto)

    @Insert
    suspend fun insertImageFiles(imageFiles: List<ImageFileDto>)

    @Query("SELECT * FROM ImageFileDto WHERE id = :id LIMIT 1")
    suspend fun getImageFile(id: String): ImageFileDto
}
