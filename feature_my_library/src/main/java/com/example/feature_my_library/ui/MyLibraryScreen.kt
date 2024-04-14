package com.example.feature_my_library.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_domain.model.comics.manga.LocalMangaItem
import com.example.core_domain.model.common.Source
import com.example.core_domain.model.common.Status
import com.example.feature_my_library.add_title.FilePicker
import com.example.feature_my_library.mvi.MyLibraryViewModel
import com.example.feature_my_library.mvi.OpenFilePickerAction
import com.example.feature_my_library.mvi.SaveFilesAction
import com.example.feature_viewer.PdfViewer
import java.io.File

@Composable
fun MyLibraryScreen(
    modifier: Modifier = Modifier,
    myLibraryViewModel: MyLibraryViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        myLibraryViewModel.init()
    }

    val state by myLibraryViewModel.model.viewState.collectAsState()

    var showPdfViewer by remember {
        mutableStateOf(false)
    }

    var currentFile by remember {
        mutableStateOf<File?>(null)
    }

    var loading by remember {
        mutableStateOf(false)
    }

    Box {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    if (!showPdfViewer) {
        Box {
            LazyColumn(modifier = modifier) {
                items(state.libraryItems) { libraryItem ->
                    val file = File(libraryItem.file.path)

                    ComicsLibraryItem(
                        modifier = Modifier.clickable {
                            currentFile = file
                            showPdfViewer = true
                        },
                        comicsItem = LocalMangaItem(
                            id = "id",
                            title = libraryItem.title,
                            totalChapters = 720,
                            currentChapter = 128,
                            localStatus = Status.Reading,
                            source = Source.Local(libraryItem.file.coverPath),
                            file = libraryItem.file
                        )
                    )
                }
            }
            FloatingActionButton(
                onClick = {
                    myLibraryViewModel.sendAction(OpenFilePickerAction)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
            if (state.showFilePicker) {
                MyFilePicker(myLibraryViewModel = myLibraryViewModel)
            }
        }
    } else {
        currentFile?.let {
            Box {
                PdfViewer(
                    file = it,
                    loadingListener = { currentState, lastState ->
                        loading = currentState.isLoading
                    },
                    onBackPressed = {
                        showPdfViewer = false
                    }
                )
            }

        }
    }
}

@Composable
fun MyFilePicker(myLibraryViewModel: MyLibraryViewModel) {
    FilePicker(
        onContentSelected = { uris ->
            myLibraryViewModel.sendAction(SaveFilesAction(uris))
        }
    )
}

fun File.copyTo(file: File) {
    inputStream().use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
}

@Preview
@Composable
fun MyLibraryScreenPreview() {
    Surface {
        MyLibraryScreen()
    }
}
