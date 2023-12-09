package com.example.core_models.source.manga

import com.example.core_models.manga.MangaListItem

interface MangaSource {

    fun getMangaList(): List<MangaListItem>
}
