package com.example.core_data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_data.database.dao.TitleFileDao
import com.example.core_data.dto.TitleFileDto

@Database(entities = [
    TitleFileDto::class
], version = 1)
abstract class RidoDatabase: RoomDatabase() {

    abstract fun getTitleFileDao(): TitleFileDao
}
