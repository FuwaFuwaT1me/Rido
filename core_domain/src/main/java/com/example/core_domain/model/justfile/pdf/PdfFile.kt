package com.example.core_domain.model.justfile.pdf

import android.os.Parcelable
import com.example.core_domain.model.common.FileData
import com.example.core_domain.model.justfile.JustFile
import kotlinx.parcelize.Parcelize

@Parcelize
data class PdfFile(
    override val id: String,
    override val title: String,
    override val file: FileData,
    override val pageCount: Int,
    override val currentPage: Int
) : JustFile, Parcelable