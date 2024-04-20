package com.example.feature_viewer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReaderDataBundle(
    val filePath: String
): Parcelable {

    companion object {
        const val NAME = "reader_data_bundle"
    }
}
