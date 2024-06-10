package com.example.impl.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.api.navigation.ReaderDataBundle
import com.example.api.navigation.ReaderDataBundleNavType
import com.example.api.navigation.ReaderNavScreen
import com.example.common.BaseScreen
import com.example.core_domain.model.common.LibraryType
import com.example.impl.mvi.ReaderViewModel
import com.example.impl.ui.ReaderScreen

@Suppress("FunctionName")
fun NavGraphBuilder.ReaderNavRoot(
    navController: NavController,
) {
    composable(
        "${ReaderNavScreen.name}/{${ReaderDataBundle.NAME}}",
        arguments = listOf(
            navArgument(ReaderDataBundle.NAME) {
                type = ReaderDataBundleNavType()
            }
        )
    ) { navBackStackEntry ->
        val viewModel = hiltViewModel<ReaderViewModel>()

        LaunchedEffect(Unit) {
            viewModel.init()
        }

        val readerScreenDataBundle = navBackStackEntry.arguments
            ?.getParcelable<ReaderDataBundle>(ReaderDataBundle.NAME)
        val libraryItemId = readerScreenDataBundle?.libraryItemId
        val libraryType = readerScreenDataBundle?.libraryType

        if (libraryItemId == null || libraryType == null) {
            // TODO: display an error
        } else {
            val type = LibraryType.valueOf(libraryType)

            BaseScreen(
                navController = navController,
                viewModel = viewModel
            ) {
                ReaderScreen(viewModel, libraryItemId, type)
            }
        }
    }
}
