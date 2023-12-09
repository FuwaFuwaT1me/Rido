package com.example.source.mangalib

import com.example.core_models.manga.Manga
import com.example.core_models.manga.MangaListItem
import com.example.core_models.source.manga.MangaSource

class MangaLibSource : MangaSource {

    private val mangaLibParser = MangaLibParser()

    override fun getMangaList(): List<MangaListItem> {
        return mangaLibParser.parseMangaList()
    }
}
