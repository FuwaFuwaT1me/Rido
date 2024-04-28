package com.example.impl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.core_domain.model.common.LibraryItem
import com.example.impl.mvi.ReaderViewModel
import java.io.File

@Composable
fun ReaderScreen(
    readerViewModel: ReaderViewModel,
    libraryItemId: String,
) {
    val libraryItem by produceState<LibraryItem?>(initialValue = null) {
        value = readerViewModel.getLibraryItem(libraryItemId)
    }

    libraryItem?.let { libItem ->
        val file = remember(libraryItem) {
            File(libItem.file.path)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            PdfViewer(
                file = file,
                startPage = libItem.currentPage,
                onDispose = { lastState ->
                    lastState.currentPage?.let { currentPage ->
                        readerViewModel.updateLastReadPage(libItem.id, currentPage)
                    }
                }
            )
        }
    }
}
