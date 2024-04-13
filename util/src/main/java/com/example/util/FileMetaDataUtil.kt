package com.example.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.media.MediaMetadataRetriever
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun getImageCover(filePath: String): Bitmap? {
    val retriever = MediaMetadataRetriever()
    try {
        retriever.setDataSource(filePath)
        val coverBytes = retriever.embeddedPicture
        if (coverBytes != null) {
            return BitmapFactory.decodeByteArray(coverBytes, 0, coverBytes.size)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        retriever.release()
    }
    return null
}

suspend fun getFirstPdfImage(file: File): String? {
    return withContext(Dispatchers.IO) {
        try {
            val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(fileDescriptor)
            val page = pdfRenderer.openPage(0)
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            pdfRenderer.close()
            fileDescriptor.close()

            saveBitmapAsPNG(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

private fun saveBitmapAsPNG(bitmap: Bitmap): String? {
    val file = File.createTempFile("cover", ".png")
    return try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        file.path
    } catch (e: IOException) {
        e.printStackTrace()
        null
        // Error occurred while saving the PNG file
    }
}


