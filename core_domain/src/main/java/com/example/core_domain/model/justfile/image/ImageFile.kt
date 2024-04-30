package com.example.core_domain.model.justfile.image

import com.example.core_domain.model.common.FileData
import com.example.core_domain.model.justfile.JustFile
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageFile(
    override val id: String,
    override val title: String,
    override val file: FileData,
) : JustFile