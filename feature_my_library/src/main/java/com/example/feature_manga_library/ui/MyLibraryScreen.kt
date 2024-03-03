package com.example.feature_manga_library.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_domain.model.comics.manga.LocalMangaItem
import com.example.core_domain.model.common.Source
import com.example.core_domain.model.common.Status
import com.example.core_domain.model.file.TitleFile
import com.example.feature_manga_library.R
import com.example.feature_manga_library.add_title.FilePicker
import com.example.feature_manga_library.mvi.MyLibraryViewModel
import com.example.feature_viewer.PdfViewer
import com.example.util.convertToByteArray
import com.example.util.getFile
import java.io.File

@Composable
fun MyLibraryScreen(
    modifier: Modifier = Modifier,
    myLibraryViewModel: MyLibraryViewModel = hiltViewModel(),
) {
    val testImage = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        R.drawable.test_image
    ).convertToByteArray()

    var showFilePicker by remember {
        mutableStateOf(false)
    }

    var showPdfViewer by remember {
        mutableStateOf(false)
    }

    val fileItems = myLibraryViewModel.titleFiles.collectAsState(emptyList())

    val context = LocalContext.current

    var currentFile by remember {
        mutableStateOf<File?>(null)
    }

    var loading by remember {
        mutableStateOf(false)
    }

    if (!showPdfViewer) {
        Box {
            LazyColumn(modifier = modifier) {
                items(fileItems.value) {
                    ComicsLibraryItem(
                        modifier = Modifier.clickable {
                            currentFile = File(it.path)
                            showPdfViewer = true
                        },
                        comicsItem = LocalMangaItem(
                            id = "id",
                            title = it.path,
                            totalChapters = 720,
                            currentChapter = 128,
                            localStatus = Status.Reading,
                            source = Source.Local(testImage)
                        )
                    )
                }
            }
            FloatingActionButton(
                onClick = {
                    showFilePicker = true
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
            if (showFilePicker) {
                FilePicker(
                    show = showFilePicker,
                    onContentSelected = { uris ->
                        val files = mutableListOf<TitleFile>()

                        uris.forEach {  uri ->
                            files.add(TitleFile(saveFile(context, uri)))
                        }

                        myLibraryViewModel.saveFiles(files)

                        showFilePicker = false
                    }
                )
            }
        }
    } else {
        currentFile?.let {
            Box {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                PdfViewer(
                    file = it,
                    loadingListener = { isLoading, currentPage, maxPage ->
                        loading = isLoading
                    },
                    onBackPressed = {
                        showPdfViewer = false
                    }
                )
            }

        }
    }
}

fun File.copyTo(file: File) {
    inputStream().use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
}

fun saveFile(context: Context, uri: Uri): String {
    val file = uri.getFile(context)
    val localDirectory = context.filesDir
    val newFile = File("${localDirectory.absolutePath}/${file?.name}")

    file?.copyTo(newFile)

    return newFile.absolutePath
}

@Preview
@Composable
fun MyLibraryScreenPreview() {
    Surface {
        MyLibraryScreen()
    }
}
