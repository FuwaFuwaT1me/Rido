package com.example.rido.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feature_my_library.ui.MyLibraryScreen
import com.example.feature_viewer.ReaderDataBundle
import com.example.feature_viewer.ReaderDataBundleNavType
import com.example.feature_viewer.ReaderScreen
import com.google.gson.Gson

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    val gson = Gson()

    NavHost(
        navController = navController,
        startDestination = Scenario.LibraryScenario.name
    ) {
        navigation(
            startDestination = Screen.MyLibraryScreen.name,
            route = Scenario.LibraryScenario.name
        ) {
            composable(Screen.MyLibraryScreen.name) {
                MyLibraryScreen(
                    navController = navController,
                    navigateToReaderScreen = { readerDataBundle ->
                        val json = Uri.encode(gson.toJson(readerDataBundle))

                        navController.navigate("${Screen.ReaderScreen.name}/$json")
                    }
                )
            }
            composable(
                "${Screen.ReaderScreen.name}/{${ReaderDataBundle.NAME}}",
                arguments = listOf(
                    navArgument(ReaderDataBundle.NAME) {
                        type = ReaderDataBundleNavType()
                    }
                )
            ) { navBackStackEntry ->
                val readerScreenDataBundle = navBackStackEntry.arguments
                    ?.getParcelable<ReaderDataBundle>(ReaderDataBundle.NAME)
                val filePath = readerScreenDataBundle?.filePath

                if (filePath == null) {
                    // TODO: display an error
                } else {
                    ReaderScreen(
                        filePath = filePath,
                        navigateBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}
