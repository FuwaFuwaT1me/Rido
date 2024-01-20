package com.example.core_domain.repository

import com.example.core_models.manga.Manga
import kotlinx.coroutines.flow.Flow

interface MangaRepository {

    suspend fun getManga(): Flow<List<Manga>>
}
