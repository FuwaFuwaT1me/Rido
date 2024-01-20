package com.example.core_domain.model

data class MangaPage(
    val id: Long,
    val currentPage: Int,
    val data: List<MangaPageItem>
)
