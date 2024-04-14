package com.example.core_data.dto.parser

import com.example.core_data.dto.TitleFileDto
import com.example.core_domain.model.file.TitleFile


fun TitleFile.toDto(): TitleFileDto {
    return TitleFileDto(
        path = this.path,
        coverPath = this.coverPath
    )
}

fun TitleFileDto.toDomain(): TitleFile {
    return TitleFile(
        path = this.path,
        coverPath = this.coverPath
    )
}
