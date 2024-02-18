package com.example.core_domain.model.comics

import com.example.core_domain.model.common.Source

interface RemoteComicsItem : ComicsItem {

    val source: Source.Remote
}
