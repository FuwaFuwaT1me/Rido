package com.example.core_domain.model.comics

import com.example.core_domain.model.common.FileData
import com.example.core_domain.model.common.PagedLibraryItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicsItem(
    override val id: String,
    override val title: String,
    override val file: FileData,
    override val pageCount: Int,
    override val currentPage: Int
) : PagedLibraryItem
