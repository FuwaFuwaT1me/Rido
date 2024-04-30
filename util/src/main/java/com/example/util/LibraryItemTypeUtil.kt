package com.example.util

import com.example.core_domain.model.comics.ComicsItem
import com.example.core_domain.model.common.LibraryItem
import com.example.core_domain.model.justfile.image.ImageFile
import com.example.core_domain.model.justfile.pdf.PdfFile

private const val COMICS = "Comics"
private const val PDF = "Pdf"
private const val IMAGE = "Image"
private const val UNKNOWN = "Unknown"

fun LibraryItem.getStringType(): String {
    return when (this) {
        is ComicsItem -> COMICS
        is ImageFile -> IMAGE
        is PdfFile -> PDF

        else -> UNKNOWN
    }
}