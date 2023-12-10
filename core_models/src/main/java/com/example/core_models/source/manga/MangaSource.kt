package com.example.core_models.source.manga

import com.example.core_models.manga.MangaListItem

interface MangaSource {

    suspend fun getMangaList(page: Int): List<MangaListItem>
}
