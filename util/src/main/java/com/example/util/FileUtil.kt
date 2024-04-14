package com.example.util

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

suspend fun File.savePdfFirstFrameToFile(outputFile: File) {
    withContext(Dispatchers.IO) {
        try {
            val fileDescriptor = ParcelFileDescriptor.open(
                this@savePdfFirstFrameToFile,
                ParcelFileDescriptor.MODE_READ_ONLY
            )
            val pdfRenderer = PdfRenderer(fileDescriptor)
            val page = pdfRenderer.openPage(0)
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            pdfRenderer.close()
            fileDescriptor.close()

            bitmap.saveAsPngToFile(outputFile)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
