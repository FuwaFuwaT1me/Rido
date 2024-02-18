package com.example.core_domain.interactor

import com.example.core_domain.model.comics.manga.LocalMangaItem
import com.example.core_domain.repository.MangaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetManga @Inject constructor(
    private val mangaRepository: MangaRepository
) {

    suspend fun getAll(): Flow<List<LocalMangaItem>> = mangaRepository.getManga()
}
