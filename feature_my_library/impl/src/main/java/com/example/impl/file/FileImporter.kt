package com.example.impl.file

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

private val SUPPORTED_FILE_TYPES = listOf(
    "pdf",
    "zip",
    "rar",
    "jpeg",
    "jpg",
    "png",
    "gif"
)

@Composable
fun FilePicker(
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

    LaunchedEffect(Unit) {
        launcher.launch(mimeTypes)
    }
}
