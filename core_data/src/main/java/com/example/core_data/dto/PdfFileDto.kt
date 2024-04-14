package com.example.core_data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.core_data.converter.FileDataDtoConverter

@Entity
@TypeConverters(FileDataDtoConverter::class)
data class PdfFileDto(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val file: FileDataDto
)