package com.example.core_database

import androidx.room.Dao
import androidx.room.Query
import com.example.core_models.manga.MangaPage
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaPageDao {

    @Query("SELECT * FROM MangaPage")
    suspend fun getMangaPagesByChapter(chapterId: String): Flow<List<MangaPage>>
}
