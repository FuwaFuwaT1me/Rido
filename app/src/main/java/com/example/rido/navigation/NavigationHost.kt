package com.example.rido.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feature_my_library.navigation.MyLibraryNavRoot
import com.example.feature_my_library.navigation.MyLibraryNavScreen
import com.example.feature_viewer.ReaderDataBundle
import com.example.feature_viewer.ReaderDataBundleNavType
import com.example.feature_viewer.ReaderScreen
import com.example.feature_viewer.navigation.ReaderNavScreen
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
            startDestination = MyLibraryNavScreen.name,
            route = Scenario.LibraryScenario.name
        ) {
//            composable(Screen.MyLibraryScreen.name) {
//                MyLibraryScreen(
//                    navigateToReaderScreen = { readerDataBundle ->
//                        val json = Uri.encode(gson.toJson(readerDataBundle))
//
//                        navController.navigate("${Screen.ReaderScreen.name}/$json")
//                    }
//                )
//            }
            MyLibraryNavRoot(this)
            // { readerDataBundle ->
            //                val json = Uri.encode(gson.toJson(readerDataBundle))
            //                navController.navigate("${ReaderNavScreen.name}/$json")
            //            }

            composable(
                "${ReaderNavScreen.name}/{${ReaderDataBundle.NAME}}",
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
