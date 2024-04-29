package com.example.impl.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.api.navigation.MyLibraryNavScreen
import com.example.common.BaseScreen
import com.example.impl.mvi.MyLibraryViewModel
import com.example.impl.ui.MyLibraryScreen

@Suppress("FunctionName")
fun NavGraphBuilder.MyLibraryNavRoot(
    navController: NavController,
) {
    composable(MyLibraryNavScreen.name) {
        val viewModel = hiltViewModel<MyLibraryViewModel>()

        LaunchedEffect(Unit) {
            viewModel.init()
        }

        BaseScreen(
            navController = navController,
            viewModel = viewModel
        ) {
            MyLibraryScreen(viewModel)
        }
    }
}
