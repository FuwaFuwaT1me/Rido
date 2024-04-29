package com.example.core_data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_data.database.dao.ImageFileDao
import com.example.core_data.database.dao.PdfFileDao
import com.example.core_data.dto.ImageFileDto
import com.example.core_data.dto.PdfFileDto

@Database(entities = [
    PdfFileDto::class,
    ImageFileDto::class,
], version = 1)
abstract class RidoDatabase: RoomDatabase() {

    abstract fun getPdfFileDao(): PdfFileDao

    abstract fun getImageFileDao(): ImageFileDao
}
