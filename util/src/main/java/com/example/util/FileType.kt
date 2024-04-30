package com.example.util

enum class FileType(val ext: String) {
    PNG("png"),
    JPEG("jpeg"),
    JPG("jpg"),
    PDF("pdf")
}

val IMAGES_TYPES = listOf(FileType.PNG, FileType.JPEG, FileType.JPG).map { it.ext }
