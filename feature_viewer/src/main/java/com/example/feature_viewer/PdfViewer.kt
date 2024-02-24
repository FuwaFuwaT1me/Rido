package com.example.feature_viewer

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.example.common.LazyColumnOrRow
import com.example.common.Orientation
import com.example.common.ZoomableImage
import com.example.util.getFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PdfViewer(
    uri: Uri,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF909090),
    pageColor: Color = Color.White,
    orientation: Orientation = Orientation.HORIZONTAL,
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    loadingListener: (
        isLoading: Boolean,
        currentPage: Int?,
        maxPage: Int?
    ) -> Unit = { _, _, _ -> },
) {
    val context = LocalContext.current
    val rendererScope = rememberCoroutineScope()
    val mutex = remember { Mutex() }
    val renderer by produceState<PdfRenderer?>(null, uri) {
        rendererScope.launch(Dispatchers.IO) {
            val input = ParcelFileDescriptor.open(uri.getFile(context), ParcelFileDescriptor.MODE_READ_ONLY)
            value = PdfRenderer(input)
        }
        awaitDispose {
            val currentRenderer = value
            rendererScope.launch(Dispatchers.IO) {
                mutex.withLock {
                    currentRenderer?.close()
                }
            }
        }
    }
    val imageLoader = LocalContext.current.imageLoader
    val imageLoadingScope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val width = with(LocalDensity.current) { maxWidth.toPx() }.toInt()
        val height = with(LocalDensity.current) { maxHeight.toPx() * 0.75 }.toInt()
        val pageCount by remember(renderer) { derivedStateOf { renderer?.pageCount ?: 0 } }

        val listState = rememberLazyListState()
        val snapFlingBehavior = rememberSnapFlingBehavior(listState)

        LazyColumnOrRow(
            listState = listState,
            orientation = orientation,
            arrangement = arrangement,
            flingBehavior = snapFlingBehavior,
            modifier = modifier
                .background(backgroundColor)
                .align(Alignment.Center)
        ) {
            items(
                count = pageCount,
                key = { index -> "$uri-$index" }
            ) { index ->
                val cacheKey = MemoryCache.Key("$uri-$index")
                val cacheValue = imageLoader.memoryCache?.get(cacheKey)?.bitmap

                var bitmap by remember {
                    mutableStateOf(cacheValue)
                }
                if (bitmap == null) {
                    DisposableEffect(uri, index) {
                        val job = imageLoadingScope.launch(Dispatchers.IO) {
                            val destinationBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                            mutex.withLock {
                                Log.d("PdfGenerator", "Loading PDF $uri - page $index/$pageCount")
                                if (!coroutineContext.isActive) return@launch

                                try {
                                    renderer?.let {
                                        it.openPage(index).use { page ->
                                            page.render(
                                                destinationBitmap,
                                                null,
                                                null,
                                                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                                            )
                                        }
                                    }
                                } catch (e: Exception) {
                                    return@launch
                                }
                            }
                            bitmap = destinationBitmap
                        }
                        onDispose {
                            job.cancel()
                        }
                    }
                } else {
                    val request = ImageRequest.Builder(context)
                        .size(width, height)
                        .memoryCacheKey(cacheKey)
                        .data(bitmap)
                        .build()

                    ZoomableImage(
                        contentScale = ContentScale.Fit,
                        painter = rememberAsyncImagePainter(request),
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
