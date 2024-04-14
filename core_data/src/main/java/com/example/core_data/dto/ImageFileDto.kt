package com.example.core_data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageFileDto(
    @PrimaryKey(autoGenerate = true) val id: Int
)