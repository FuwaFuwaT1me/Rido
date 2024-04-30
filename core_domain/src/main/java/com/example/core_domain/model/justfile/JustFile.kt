package com.example.core_domain.model.justfile

import com.example.core_domain.model.common.LibraryItem
import com.example.core_domain.model.common.PagedLibraryItem

interface JustFile : LibraryItem

interface PagedFile : PagedLibraryItem, JustFile
