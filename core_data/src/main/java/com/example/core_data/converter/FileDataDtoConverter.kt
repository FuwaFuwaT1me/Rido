package com.example.core_data.converter

import androidx.room.TypeConverter
import com.example.core_data.dto.FileDataDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FileDataDtoConverter {

    @TypeConverter
    fun fromFileDataDto(fileData: FileDataDto): String {
        return Json.encodeToString(fileData)
    }

    @TypeConverter
    fun toFileDataDto(string: String): FileDataDto {
        return Json.decodeFromString<FileDataDto>(string)
    }
}