package com.example.util

import android.graphics.Bitmap
import java.nio.ByteBuffer

fun Bitmap.convertToByteArray(): ByteArray = ByteBuffer.allocate(byteCount).apply {
    copyPixelsToBuffer(this)
    rewind()
}.array()
