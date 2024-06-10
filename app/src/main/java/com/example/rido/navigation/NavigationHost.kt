package com.example.rido.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.api.navigation.MyLibraryNavScreen
import com.example.impl.navigation.MyLibraryNavRoot
import com.example.impl.navigation.ReaderNavRoot

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MyLibraryNavScreen
    ) {
        BackStackReaderScenario(navController)
    }
}

private fun NavGraphBuilder.BackStackReaderScenario(navController: NavController) {
    MyLibraryNavRoot(navController)
    ReaderNavRoot(navController)
}
