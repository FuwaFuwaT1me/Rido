package com.example.core_data.dto.parser

import com.example.core_data.dto.MangaDto
import com.example.core_models.manga.Manga

fun Manga.toDto(): MangaDto = MangaDto(
    id = id,
    title = title
)

fun MangaDto.toModel(): Manga = Manga(
    id = id,
    title = title
)
