package com.example.core_domain.model.common

import android.os.Parcelable

interface LibraryItem : Parcelable   {

    val id: String
    val title: String
    val file: FileData
    val pageCount: Int
    val currentPage: Int
}
