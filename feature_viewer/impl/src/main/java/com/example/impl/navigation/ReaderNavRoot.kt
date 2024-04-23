package com.example.impl.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.api.navigation.ReaderDataBundle
import com.example.api.navigation.ReaderDataBundleNavType
import com.example.api.navigation.ReaderNavScreen
import com.example.common.BaseScreen
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

        val readerScreenDataBundle = navBackStackEntry.arguments
            ?.getParcelable<ReaderDataBundle>(ReaderDataBundle.NAME)
        val filePath = readerScreenDataBundle?.filePath

        if (filePath == null) {
            // TODO: display an error
        } else {
            BaseScreen(
                navController = navController,
                viewModel = viewModel
            ) {
                ReaderScreen(viewModel, filePath)
            }
        }
    }
}
