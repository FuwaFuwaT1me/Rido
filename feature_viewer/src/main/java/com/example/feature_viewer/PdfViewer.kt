package com.example.feature_viewer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.annotation.RawRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

enum class PdfListDirection {
    HORIZONTAL,
    VERTICAL
}

@Composable
fun PdfViewer(
    pdfStream: InputStream,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF909090),
    pageColor: Color = Color.White,
    listDirection: PdfListDirection = PdfListDirection.HORIZONTAL,
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(16.dp),
    loadingListener: (
        isLoading: Boolean,
        currentPage: Int?,
        maxPage: Int?
    ) -> Unit = { _, _, _ -> },
) {
    PdfViewer(
        pdfStream = pdfStream,
        modifier = modifier,
        backgroundColor = backgroundColor,
        listDirection = listDirection,
        loadingListener = loadingListener,
        arrangement = arrangement
    ) { lazyState, image ->
        PdfPage(
            image = image,
            lazyState = lazyState,
            backgroundColor = pageColor
        )
    }
}

@Composable
fun PdfViewer(
    pdfStream: InputStream,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF909090),
    listDirection: PdfListDirection = PdfListDirection.HORIZONTAL,
    arrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(16.dp),
    loadingListener: (
        isLoading: Boolean,
        currentPage: Int?,
        maxPage: Int?
    ) -> Unit = { _, _, _ -> },
    page: @Composable (LazyListState, ImageBitmap) -> Unit
) {
    val context = LocalContext.current
    val pagePaths = remember {
        mutableStateListOf<String>()
    }

    LaunchedEffect(true) {
        if (pagePaths.isNotEmpty()) {
            val paths = context.loadPdf(pdfStream, loadingListener)
            pagePaths.addAll(paths)
        }
    }

    val lazyState = rememberLazyListState()
    when (listDirection) {
        PdfListDirection.HORIZONTAL -> {
            LazyRow(
                modifier = modifier.background(backgroundColor),
                state = lazyState,
                horizontalArrangement = arrangement
            ) {
                items(pagePaths) { path ->
                    var imageBitmap by remember {
                        mutableStateOf<ImageBitmap?>(null)
                    }

                    LaunchedEffect(path) {
                        imageBitmap = BitmapFactory.decodeFile(path).asImageBitmap()
                    }
                    imageBitmap?.let {
                        page(lazyState, it)
                    }
                }
            }
        }
        PdfListDirection.VERTICAL -> {
            LazyColumn(
                modifier = modifier.background(backgroundColor),
                state = lazyState,
                verticalArrangement = arrangement
            ) {
                items(pagePaths) { path ->
                    var imageBitmap by remember {
                        mutableStateOf<ImageBitmap?>(null)
                    }

                    LaunchedEffect(path) {
                        imageBitmap = BitmapFactory.decodeFile(path).asImageBitmap()
                    }
                    imageBitmap?.let {
                        page(lazyState, it)
                    }
                }
            }
        }
    }
}

@Composable
private fun PdfPage(
    image: ImageBitmap,
    lazyState: LazyListState,
    backgroundColor: Color
) {
    Card(
        modifier = Modifier.background(backgroundColor),
    ) {
        ZoomableImage(painter = BitmapPainter(image), scrollState = lazyState)
    }
}

suspend fun Context.loadPdf(
    inputStream: InputStream,
    loadingListener: (
            isLoading: Boolean,
            currentPage: Int?,
            maxPage: Int?
    ) -> Unit = { _, _, _ -> }
): List<String> {
    return withContext(Dispatchers.IO) {
        loadingListener(true, null, null)

        val outputDir = cacheDir
        val tempFile = File.createTempFile("temp", "pdf", outputDir)

        tempFile.mkdirs()
        tempFile.deleteOnExit()

        val outputStream = FileOutputStream(tempFile)
        copy(inputStream, outputStream)

        val input = ParcelFileDescriptor.open(
            tempFile,
            ParcelFileDescriptor.MODE_READ_ONLY
        )
        val renderer = PdfRenderer(input)

        (0 until renderer.pageCount).map { pageNumber ->
            loadingListener(true, pageNumber, renderer.pageCount)

            val file = File.createTempFile("PDFPage$pageNumber", "png", outputDir)
            file.mkdirs()
            file.deleteOnExit()

            val page = renderer.openPage(pageNumber)
            val bitmap = Bitmap.createBitmap(1240, 1754, Bitmap.Config.ARGB_8888)

            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(file))
            Log.i("PDF_VIEWER", "Loaded page $pageNumber")

            file.absolutePath.also { Log.d("RIDO", it) }
        }. also {
            loadingListener(false, null, renderer.pageCount)
            renderer.close()
        }
    }
}

@Throws(IOException::class)
private fun copy(source: InputStream, target: OutputStream) {
    val buf = ByteArray(8192)
    var length: Int
    while (source.read(buf).also { length = it } > 0) {
        target.write(buf, 0, length)
    }
}
