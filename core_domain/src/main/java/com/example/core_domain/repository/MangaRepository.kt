package com.example.core_domain.repository

import com.example.core_domain.model.comics.manga.LocalMangaItem
import kotlinx.coroutines.flow.Flow

interface MangaRepository {

    suspend fun getManga(): Flow<List<LocalMangaItem>>
}
