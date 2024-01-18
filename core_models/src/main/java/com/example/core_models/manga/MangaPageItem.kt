package com.example.core_models.manga

import com.google.gson.annotations.SerializedName

data class MangaPageItem(
    val id: Int,
    val name: String,
    val engName: String,
    val rusName: String,
    val cover: String,
)
