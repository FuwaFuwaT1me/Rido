package com.example.core_data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TitleFileDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val path: String
)
