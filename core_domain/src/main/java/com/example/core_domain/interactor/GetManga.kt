package com.example.core_domain.interactor

import com.example.core_domain.model.Manga
import com.example.core_domain.repository.MangaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetManga @Inject constructor(
    private val mangaRepository: MangaRepository
) {

    suspend fun getAll(): Flow<List<Manga>> = mangaRepository.getManga()
}
