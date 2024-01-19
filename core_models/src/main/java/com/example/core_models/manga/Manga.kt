package com.example.core_models.manga

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Manga(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String
)
