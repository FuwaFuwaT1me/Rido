package com.example.feature_manga_library.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_domain.model.comics.LocalComicsItem
import com.example.core_domain.model.comics.manga.LocalMangaItem
import com.example.core_domain.model.common.Source
import com.example.core_domain.model.common.Status
import com.example.feature_manga_library.R
import com.example.util.convertToByteArray

@Composable
fun ComicsLibraryItem(
    comicsItem: LocalComicsItem,
    modifier: Modifier = Modifier
) {
    val percent = ((comicsItem.currentChapter * 1.0 / comicsItem.totalChapters) * 100).toInt()

    Row(
        modifier = modifier,
    ) {
        AsyncImage(
            model = comicsItem.source.resource,
            contentDescription = null,
            placeholder = painterResource(R.drawable.test_image),
            modifier = Modifier
                .height(100.dp)
                .width(50.dp)
        )
        Column {
            Text(
                text = comicsItem.title
            )
            Row {
                Text(
                    text = "Chapters ${comicsItem.currentChapter} / ${comicsItem.totalChapters} ($percent%)"
                )
            }
            Text(
                text = "Last status update"
            )
        }
    }
}

@Preview
@Composable
fun MangaLibraryItemPreview() {
    val testImage = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        R.drawable.test_image
    ).convertToByteArray()

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
