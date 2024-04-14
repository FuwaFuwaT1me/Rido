package com.example.core_domain.model.common

import kotlinx.serialization.Serializable

@Serializable
data class FileData(
    val path: String,
    val coverPath: String
)
