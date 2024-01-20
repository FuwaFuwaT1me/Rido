package com.example.feature_manga_library.repository

import com.example.core_data.database.MangaDao
import com.example.core_domain.repository.MangaRepository
import com.example.core_models.manga.Manga
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val mangaDao: MangaDao
): MangaRepository {

    override suspend fun getManga(): Flow<List<Manga>> {
        return mangaDao.getManga()
    }
}
