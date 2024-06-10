package com.example.impl.ui.pdf

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest
import coil.size.Size
import com.example.common.HorizontalOrVerticalPager
import com.example.common.Orientation
import com.example.common.ZoomableImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

data class PdfViewerState(
    val isLoading: Boolean,
    val currentPage: Int?,
    val maxPage: Int?,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PdfViewer(
    file: File,
    modifier: Modifier = Modifier,
    startPage: Int = 0,
    orientation: Orientation = Orientation.HORIZONTAL,
    loadingListener: (
        currentState: PdfViewerState,
    ) -> Unit = { _ -> },
    onDispose: (lastState: PdfViewerState) -> Unit = {}
) {
    var lastState by remember {
        mutableStateOf(PdfViewerState(false, null, null))
    }

    fun updateState(newState: PdfViewerState) {
        if (newState != lastState) {
            loadingListener(newState)
            lastState = newState
        }
    }

    LaunchedEffect(Unit) {
        updateState(lastState.copy(isLoading = true))
    }

    val context = LocalContext.current
    val rendererScope = rememberCoroutineScope()
    val mutex = remember { Mutex() }
    val renderer by produceState<PdfRenderer?>(null, file) {
        rendererScope.launch(Dispatchers.IO) {
            val input = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
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
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val width = with(LocalDensity.current) { maxWidth.toPx() }.toInt()
        val widthDp = maxWidth
        val height = with(LocalDensity.current) { maxHeight.toPx() * 0.75 }.toInt()
        val heightDp = maxHeight.times(0.75f)
        val pageCount by remember(renderer) { derivedStateOf { renderer?.pageCount ?: 0 } }

        val lifecycle = LocalLifecycleOwner.current.lifecycle

        val pagerState = rememberPagerState(initialPage = startPage) {
            pageCount
        }
        // TODO: maybe use snapFlingBehavior
        val flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(10)
        )

        DisposableEffect(Unit) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_STOP) {
                    onDispose(PdfViewerState(false, lastState.currentPage, 0))
                }
            }
            lifecycle.addObserver(observer)
            onDispose {
                lifecycle.removeObserver(observer)
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                updateState(PdfViewerState(false, page, pageCount))
            }
        }

        var pagerScrollEnabled by remember {
            mutableStateOf(true)
        }

        HorizontalOrVerticalPager(
            pagerState = pagerState,
            orientation = orientation,
            flingBehavior = flingBehavior,
            beyondBoundPageCount = 0,
            userScrollEnabled = true,
            modifier = modifier
                .size(widthDp, heightDp)
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(Color.White)
        ) { index ->
            val cacheKey = MemoryCache.Key("$file-$index")
            val cacheValue = imageLoader.memoryCache?.get(cacheKey)?.bitmap

            var bitmap by remember {
                mutableStateOf(cacheValue)
            }
            if (bitmap == null) {
                DisposableEffect(file, index) {
                    val job = imageLoadingScope.launch(Dispatchers.IO) {
                        val destinationBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                        mutex.withLock {
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
                    .memoryCacheKey(cacheKey)
                    .data(bitmap)
                    .size(Size.ORIGINAL)
                    .build()

                val imageScrollState = rememberScrollState()

                LaunchedEffect(imageScrollState.isScrollInProgress) {
                    pagerScrollEnabled = !imageScrollState.isScrollInProgress
                    Log.d("ANIME", "${imageScrollState.isScrollInProgress}")
                }

                ZoomableImage(
                    contentScale = ContentScale.Fit,
                    painter = rememberAsyncImagePainter(request),
                    modifier = Modifier
                        .fillMaxWidth(),
                    scrollState = imageScrollState
                )
            }
        }
        if (lastState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
