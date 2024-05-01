package com.example.api.navigation

import android.os.Parcelable
import com.example.common.navigation.DataBundle
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReaderDataBundle(
    val libraryItemId: String,
    val libraryType: String
): Parcelable, DataBundle {

    companion object {
        const val NAME = "reader_data_bundle"
    }
}
