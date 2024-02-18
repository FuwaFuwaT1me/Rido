package com.example.core_domain.model.comics.manga

import com.example.core_domain.model.comics.ComicsItem
import com.example.core_domain.model.comics.LocalComicsItem
import com.example.core_domain.model.comics.RemoteComicsItem
import com.example.core_domain.model.common.Source
import com.example.core_domain.model.common.Status

data class RemoteMangaItem(
    override val id: String,
    override val title: String,
    override val totalChapters: Int,
    override val currentChapter: Int,
    override val localStatus: Status,
    override val source: Source.Remote
) : RemoteComicsItem
