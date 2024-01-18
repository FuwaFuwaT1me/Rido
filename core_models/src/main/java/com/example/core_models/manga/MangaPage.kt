package com.example.core_models.manga

import com.google.gson.annotations.SerializedName

data class MangaPage(
    val currentPage: Int,
    val data: List<MangaPageItem>
)
