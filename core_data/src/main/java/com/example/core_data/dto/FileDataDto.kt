package com.example.core_data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FileDataDto(
    val path: String,
    val coverPath: String
)
