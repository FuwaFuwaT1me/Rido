package com.example.core_data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Manga")
data class MangaDto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String
)
