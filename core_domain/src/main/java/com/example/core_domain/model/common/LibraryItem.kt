package com.example.core_domain.model.common

import android.os.Parcelable

interface LibraryItem : Parcelable {
    val id: String
    val title: String
    val file: FileData
}

interface PagedLibraryItem : LibraryItem {
    val pageCount: Int
    val currentPage: Int
}
