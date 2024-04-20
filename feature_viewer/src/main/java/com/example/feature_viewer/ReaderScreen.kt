package com.example.feature_viewer

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.io.File

@Composable
fun ReaderScreen(
    filePath: String,
    navigateBack: () -> Unit
) {
    BackHandler {
        navigateBack()
    }

    val file = remember(filePath) {
        File(filePath)
    }

    PdfViewer(
        file = file
    )
}