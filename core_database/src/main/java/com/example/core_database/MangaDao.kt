package com.example.core_database

import androidx.room.Dao
import androidx.room.Query
import com.example.core_models.manga.Manga
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {

    @Query("SELECT * FROM Manga")
    suspend fun getManga(): Flow<List<Manga>>
}
