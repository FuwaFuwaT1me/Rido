package com.example.core_data.dto.parser

import com.example.core_data.dto.MangaDto
import com.example.core_domain.model.Manga

fun com.example.core_domain.model.Manga.toDto(): MangaDto = MangaDto(
    id = id,
    title = title
)

fun MangaDto.toModel(): com.example.core_domain.model.Manga = com.example.core_domain.model.Manga(
    id = id,
    title = title
)
