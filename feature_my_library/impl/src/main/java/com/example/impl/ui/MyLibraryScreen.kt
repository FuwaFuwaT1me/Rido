package com.example.impl.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.api.navigation.NavigateToReader
import com.example.api.navigation.ReaderDataBundle
import com.example.core_domain.model.comics.ComicsItem
import com.example.impl.file.FilePicker
import com.example.impl.mvi.MyLibraryViewModel
import com.example.impl.mvi.OpenFilePickerAction
import com.example.impl.mvi.SaveFilesAction
import java.io.File

@Composable
fun MyLibraryScreen(
    myLibraryViewModel: MyLibraryViewModel,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        myLibraryViewModel.init()
    }

    val state by myLibraryViewModel.model.viewState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = modifier) {
            items(state.libraryItems) { libraryItem ->
                ComicsLibraryItem(
                    modifier = Modifier.clickable {
                        val readerDataBundle = ReaderDataBundle(libraryItem.id)
                        val navEvent = NavigateToReader(readerDataBundle)
                        myLibraryViewModel.sendNavigationEvent(navEvent)
                    },
                    comicsItem = ComicsItem(
                        id = "id",
                        title = libraryItem.title,
                        pageCount = libraryItem.pageCount,
                        currentPage = libraryItem.currentPage,
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
//        MyLibraryScreen()
    }
}
