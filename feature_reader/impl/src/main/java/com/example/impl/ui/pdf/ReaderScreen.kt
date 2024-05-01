package com.example.impl.ui.pdf

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.core_domain.model.common.LibraryItem
import com.example.core_domain.model.common.LibraryType
import com.example.core_domain.model.justfile.image.ImageFile
import com.example.core_domain.model.justfile.pdf.PdfFile
import com.example.impl.mvi.ReaderLoaded
import com.example.impl.mvi.ReaderStartLoading
import com.example.impl.mvi.ReaderViewModel
import com.example.impl.ui.image.ImageViewer
import java.io.File

@Composable
fun ReaderScreen(
    readerViewModel: ReaderViewModel,
    libraryItemId: String,
    libraryType: LibraryType,
) {
    val libraryItem by produceState<LibraryItem?>(initialValue = null) {
        value = readerViewModel.getLibraryItem(libraryItemId, libraryType)
    }

    libraryItem?.let { libItem ->
        val file = remember(libraryItem) {
            File(libItem.file.path)
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
        ) {
            when (libItem) {
                is PdfFile -> {
                    PdfViewer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        file = file,
                        startPage = libItem.currentPage,
                        onDispose = { lastState ->
                            lastState.currentPage?.let { currentPage ->
                                readerViewModel.updateLastReadPage(libItem.id, currentPage)
                            }
                        },
                        loadingListener = { newState ->
                            if (newState.isLoading) {
                                readerViewModel.sendAction(ReaderStartLoading)
                            } else {
                                readerViewModel.sendAction(ReaderLoaded)
                            }
                        }
                    )
                }

                is ImageFile -> {
                    ImageViewer(
                        modifier = Modifier,
                        libItemId = libItem.id,
                        file = file,
                    )
                }
            }
        }
    }
}
