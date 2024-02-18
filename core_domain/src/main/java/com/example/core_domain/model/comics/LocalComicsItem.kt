package com.example.core_domain.model.comics

import com.example.core_domain.model.common.Source

interface LocalComicsItem : ComicsItem {

    val source: Source.Local
}
