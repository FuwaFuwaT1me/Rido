package com.example.impl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.impl.mvi.ReaderViewModel
import java.io.File

@Composable
fun ReaderScreen(
    readerViewModel: ReaderViewModel,
    filePath: String,
) {
    val file = remember(filePath) {
        File(filePath)
    }

    PdfViewer(
        file = file
    )
}