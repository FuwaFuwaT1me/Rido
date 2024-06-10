package com.example.api.navigation

import android.os.Parcelable
import com.example.common.navigation.DataBundle
import com.example.core_domain.model.common.LibraryType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

@Serializable
@Parcelize
data class ReaderDataBundle(
    val libraryItemId: String,
    val libraryType: LibraryType
): Parcelable, DataBundle {

    override fun toString(): String {
        return Json.encodeToJsonElement(this).toString()
    }
}
