package com.example.core_domain.model.comics

import com.example.core_domain.model.common.Source
import com.example.core_domain.model.common.Status

interface ComicsItem {

    val id: String
    val title: String
    val totalChapters: Int
    val currentChapter: Int
    val localStatus: Status
}
