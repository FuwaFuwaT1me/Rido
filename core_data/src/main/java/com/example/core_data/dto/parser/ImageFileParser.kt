package com.example.core_data.dto.parser

import com.example.core_data.dto.ImageFileDto
import com.example.core_domain.model.justfile.image.ImageFile

fun ImageFile.toDto(): ImageFileDto {
    return ImageFileDto(
        id = this.id.toInt(),
        title = this.title,
        file = this.file.toDto(),
    )
}

fun ImageFileDto.toDomain(): ImageFile {
    return ImageFile(
        id = this.id.toString(),
        title = this.title,
        file = this.file.toDomain(),
    )
}