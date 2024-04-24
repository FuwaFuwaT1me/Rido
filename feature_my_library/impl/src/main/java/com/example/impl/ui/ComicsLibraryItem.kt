package com.example.impl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core_domain.model.comics.LocalComicsItem

@Composable
fun ComicsLibraryItem(
    comicsItem: LocalComicsItem,
    modifier: Modifier = Modifier
) {
    val percent = ((comicsItem.currentChapter * 1.0 / comicsItem.totalChapters) * 100).toInt()

    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(comicsItem.file.coverPath)
                .build(),
            contentDescription = "Bitmap image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(100.dp)
                .width(75.dp)
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
//    val testImage = BitmapFactory.decodeResource(
//        LocalContext.current.resources,
//        R.drawable.test_image
//    )
// TODO: pass test image file path
//    ComicsLibraryItem(
//        comicsItem = LocalMangaItem(
//            id = "id",
//            title = "Naruto",
//            totalChapters = 720,
//            currentChapter = 128,
//            localStatus = Status.Reading,
//            source = Source.Local(LocalContext.current.getDrawable(R.drawable.test_image).)
//        )
//    )
}
