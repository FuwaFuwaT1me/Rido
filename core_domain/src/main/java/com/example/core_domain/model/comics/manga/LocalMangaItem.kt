package com.example.core_domain.model.comics.manga

import com.example.core_domain.model.comics.LocalComicsItem
import com.example.core_domain.model.common.Source
import com.example.core_domain.model.common.Status
import com.example.core_domain.model.common.FileData

data class LocalMangaItem(
    override val id: String,
    override val title: String,
    override val totalChapters: Int,
    override val currentChapter: Int,
    override val localStatus: Status,
    override val source: Source.Local,
    override val file: FileData
) : LocalComicsItem
