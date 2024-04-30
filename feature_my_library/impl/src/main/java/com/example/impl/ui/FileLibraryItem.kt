package com.example.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core_domain.model.justfile.JustFile
import com.example.core_domain.model.justfile.PagedFile
import com.example.util.getStringType

@Composable
fun FileLibraryItem(
    file: JustFile,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(file.file.coverPath)
                .build(),
            contentDescription = "Bitmap image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(100.dp)
                .width(75.dp)
                .background(Color.White)
        )
        Column {
            Text(
                text = file.title
            )
            Row {
                (file as? PagedFile)?.let {
                    val percent = ((file.currentPage * 1.0 / file.pageCount) * 100).toInt()
                    Text(
                        text = "Chapters ${file.currentPage} / ${file.pageCount} ($percent%)"
                    )
                }
                Text(text = file.getStringType())
            }
            Text(
                text = "Last status update"
            )
        }
    }
}