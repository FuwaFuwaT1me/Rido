package com.example.core_data.dto.parser

import com.example.core_data.dto.PdfFileDto
import com.example.core_domain.model.justfile.pdf.PdfFile


fun PdfFile.toDto(): PdfFileDto {
    return PdfFileDto(
        id = this.id.toInt(),
        title = this.title,
        file = this.file.toDto(),
        pageCount = this.pageCount,
        currentPage = this.currentPage
    )
}

fun PdfFileDto.toDomain(): PdfFile {
    return PdfFile(
        id = this.id.toString(),
        title = this.title,
        file = this.file.toDomain(),
        pageCount = this.pageCount,
        currentPage = this.currentPage
    )
}
