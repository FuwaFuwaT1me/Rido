package com.example.core_data.dto.parser

import com.example.core_data.dto.FileDataDto
import com.example.core_domain.model.common.FileData


fun FileData.toDto(): FileDataDto {
    return FileDataDto(
        path = this.path,
        coverPath = this.coverPath
    )
}

fun FileDataDto.toDomain(): FileData {
    return FileData(
        path = this.path,
        coverPath = this.coverPath
    )
}
