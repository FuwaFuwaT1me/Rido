package com.example.impl.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.api.navigation.ReaderDataBundle
import com.example.api.navigation.ReaderDataBundleNavType
import com.example.api.navigation.ReaderNavScreen
import com.example.common.BaseScreen
import com.example.impl.mvi.ReaderViewModel
import com.example.impl.ui.ReaderScreen
import kotlin.reflect.typeOf

@Suppress("FunctionName")
fun NavGraphBuilder.ReaderNavRoot(
    navController: NavController,
) {
    composable<ReaderNavScreen>(
        typeMap = mapOf(
            typeOf<ReaderDataBundle>() to ReaderDataBundleNavType()
        )
    ) { navBackStackEntry ->
        val viewModel = hiltViewModel<ReaderViewModel>()

        LaunchedEffect(Unit) {
            viewModel.init()
        }

        val dataBundle = navBackStackEntry.toRoute<ReaderNavScreen>().dataBundle
        val libraryItemId = dataBundle.libraryItemId
        val libraryType = dataBundle.libraryType

        BaseScreen(
            navController = navController,
            viewModel = viewModel
        ) {
            ReaderScreen(viewModel, libraryItemId, libraryType)
        }
    }
}
