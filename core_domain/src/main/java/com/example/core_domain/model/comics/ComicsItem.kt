package com.example.core_domain.model.comics

import com.example.core_domain.model.common.LibraryItem
import com.example.core_domain.model.common.Source
import com.example.core_domain.model.common.Status

interface ComicsItem : LibraryItem {

    val totalChapters: Int
    val currentChapter: Int
    val localStatus: Status
}
