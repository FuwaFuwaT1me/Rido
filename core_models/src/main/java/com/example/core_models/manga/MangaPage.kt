package com.example.core_models.manga

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MangaPage(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val currentPage: Int,
    val data: List<MangaPageItem>
)
