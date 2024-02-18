package com.example.feature_manga_library.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feature_manga_library.mvi.MangaLibraryModel
import com.example.feature_manga_library.mvi.MangaLibraryState
import com.example.feature_manga_library.mvi.MangaLibraryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun MangaLibraryScreen(
    modifier: Modifier = Modifier,
    mangaLibraryViewModel: MangaLibraryViewModel = viewModel()
) {
    Column(modifier = modifier) {

    }
}
