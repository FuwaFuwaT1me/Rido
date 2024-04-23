package com.example.impl.ui

import android.net.Uri
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.api.navigation.NavigateToReader
import com.example.api.navigation.ReaderDataBundle
import com.example.common.navigation.DataBundle
import com.example.core_domain.model.comics.manga.LocalMangaItem
import com.example.core_domain.model.common.Source
import com.example.core_domain.model.common.Status
import com.example.impl.add_title.FilePicker
import com.example.impl.mvi.MyLibraryViewModel
import com.example.impl.mvi.OpenFilePickerAction
import com.example.impl.mvi.SaveFilesAction
import com.google.gson.Gson
import kotlinx.coroutines.launch
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

    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = modifier) {
            items(state.libraryItems) { libraryItem ->
                ComicsLibraryItem(
                    modifier = Modifier.clickable {
                        scope.launch {
                            val readerDataBundle = ReaderDataBundle(
                                filePath = libraryItem.file.path
                            )

                            val navEvent = NavigateToReader(readerDataBundle)
                            myLibraryViewModel.sendNavigationEvent(navEvent)
                        }

//                        navController.navigate(buildRoute("reader_screen", readerDataBundle))
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
}

private fun buildRoute(route: String, dataBundle: DataBundle): String {
    val encodedDataBundle = Uri.encode(Gson().toJson(dataBundle))
    return "$route/$encodedDataBundle"
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
