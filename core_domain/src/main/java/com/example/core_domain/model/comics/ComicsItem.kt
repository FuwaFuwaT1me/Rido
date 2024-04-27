package com.example.core_domain.model.comics

import android.os.Parcelable
import com.example.core_domain.model.common.FileData
import com.example.core_domain.model.common.LibraryItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicsItem(
    override val id: String,
    override val title: String,
    override val file: FileData,
    override val pageCount: Int,
    override val currentPage: Int
) : LibraryItem, Parcelable
