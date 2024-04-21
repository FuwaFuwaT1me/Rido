package com.example.feature_my_library.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature_my_library.ui.MyLibraryScreen
import com.example.feature_viewer.ReaderDataBundle

@Suppress("FunctionName")
fun MyLibraryNavRoot(
    navGraphBuilder: NavGraphBuilder,
) {
    navGraphBuilder.apply {
        composable(MyLibraryNavScreen.name) {
            MyLibraryScreen()
        }
    }
}

//val json = Uri.encode(gson.toJson(readerDataBundle))
//
//                    navController.navigate("${Screen.ReaderScreen.name}/$json")