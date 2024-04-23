package com.example.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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

    Box(modifier = Modifier.fillMaxSize()) {
        PdfViewer(
            file = file
        )
    }
}
