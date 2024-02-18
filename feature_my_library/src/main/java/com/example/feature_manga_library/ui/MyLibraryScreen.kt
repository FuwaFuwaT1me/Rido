package com.example.feature_manga_library.ui

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import com.example.feature_manga_library.R
import com.example.feature_manga_library.add_title.FilePicker
import com.example.feature_manga_library.mvi.MyLibraryViewModel
import com.example.util.convertToByteArray

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

    Box {
        LazyColumn(modifier = modifier) {
            items(30) {
                ComicsLibraryItem(
                    comicsItem = LocalMangaItem(
                        id = "id",
                        title = "Naruto",
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
                    Log.d("anime", "$uris")
                    showFilePicker = false
                }
            )
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
