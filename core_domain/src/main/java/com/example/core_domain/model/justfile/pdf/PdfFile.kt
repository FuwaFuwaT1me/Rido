package com.example.core_domain.model.justfile.pdf

import com.example.core_domain.model.common.FileData
import com.example.core_domain.model.justfile.JustFile

data class PdfFile(
    override val id: String,
    override val title: String,
    override val file: FileData
) : JustFile