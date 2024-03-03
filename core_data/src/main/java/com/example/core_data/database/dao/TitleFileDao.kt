package com.example.core_data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core_data.dto.TitleFileDto
import kotlinx.coroutines.flow.Flow

@Dao
interface TitleFileDao {

    @Query("SELECT * FROM TitleFileDto")
    fun getTitleFiles(): Flow<List<TitleFileDto>>

    @Insert
    fun insertTitleFile(titleFileDto: TitleFileDto)

    @Insert
    fun insertTitleFiles(titleFileDtos: List<TitleFileDto>)
}
