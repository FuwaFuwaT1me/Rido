package com.example.feature_manga_library.add_title

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

private val SUPPORTED_FILE_TYPES = listOf(
    "pdf",
    "zip",
    "rar"
)

@Composable
fun FilePicker(
    show: Boolean,
    onContentSelected: (List<Uri>) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) {
        onContentSelected(it)
    }

    val mimeTypeMap = MimeTypeMap.getSingleton()
    val mimeTypes = SUPPORTED_FILE_TYPES.mapNotNull { ext ->
        mimeTypeMap.getMimeTypeFromExtension(ext)
    }.toTypedArray()

    LaunchedEffect(show) {
        if (show) {
            launcher.launch(mimeTypes)
        }
    }
}

fun Uri.getFile (context: Context): File? {
    val fileDescriptor = context.contentResolver.openFileDescriptor(this, "r", null) ?: return null

    val file = File(context.cacheDir, getFileName(context)!!)
    val fileOutputStream = FileOutputStream(file)

    val fileInputStream = FileInputStream(fileDescriptor.fileDescriptor)
    fileInputStream.copyTo(fileOutputStream)
    fileDescriptor.close()

    return file
}

fun Uri.getFileName (context: Context): String? {
    val cursor = context.contentResolver.query(this, null, null, null, null)
    if (cursor == null || !cursor.moveToFirst()) return null

    val indexName = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    val fileName = cursor.getString(indexName)
    cursor.close()

    return fileName
}
