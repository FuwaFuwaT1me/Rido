package com.example.feature_manga_library.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_domain.model.comics.manga.LocalMangaItem
import com.example.core_domain.model.common.Source
import com.example.core_domain.model.common.Status
import com.example.feature_manga_library.R
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
}

@Preview
@Composable
fun MyLibraryScreenPreview() {
    Surface {
        MyLibraryScreen()
    }
}
