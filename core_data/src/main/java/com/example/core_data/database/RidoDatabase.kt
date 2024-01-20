package com.example.core_data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [
    MangaDao::class,
    MangaPageDao::class
], version = 1)
abstract class RidoDatabase: RoomDatabase() {

    abstract fun getMangaDao(): MangaDao
    abstract fun getMangaPageDao(): MangaPageDao
}
