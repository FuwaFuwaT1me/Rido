package com.example.core_domain.model.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileData(
    val path: String,
    val coverPath: String
) : Parcelable
