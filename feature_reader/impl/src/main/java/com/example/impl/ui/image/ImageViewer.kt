package com.example.impl.ui.image

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.example.common.ZoomableImage
import java.io.File

@Composable
fun ImageViewer(
    libItemId: String,
    file: File,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageLoader =  context.imageLoader
    val cacheKey = MemoryCache.Key(libItemId)
    val cacheValue = imageLoader.memoryCache?.get(cacheKey)?.bitmap

    var bitmap by remember {
        mutableStateOf(cacheValue)
    }

    Box(modifier = modifier
        .fillMaxSize()
    ) {
        if (bitmap == null) {
            DisposableEffect(file) {
                val inputStream = file.inputStream()
                bitmap = BitmapFactory.decodeStream(inputStream)

                onDispose {
                    inputStream.close()
                }
            }
            CircularProgressIndicator()
        } else {
            val request = ImageRequest.Builder(context)
                .memoryCacheKey(cacheKey)
                .data(bitmap)
                .build()

            ZoomableImage(
                modifier = Modifier
                    .fillMaxSize(),
                painter = rememberAsyncImagePainter(request),
                contentScale = ContentScale.Fit,
            )
        }
    }
}