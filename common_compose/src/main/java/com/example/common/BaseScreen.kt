package com.example.common

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.common.navigation.DataBundle
import com.example.core.mvi.api.Action
import com.example.core.mvi.api.Event
import com.example.core.mvi.api.MviNavigationEvent
import com.example.core.mvi.api.State
import com.example.core.mvi.impl.BaseNavigationEvent
import com.example.core.mvi.impl.BaseViewModel
import com.google.gson.Gson

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    navController: NavController,
    viewModel: BaseViewModel<out Action, out Event, out State, out MviNavigationEvent>,
    content: @Composable (NavController) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.model.navigationEvent.collect { navEvent ->
            when (navEvent) {
                is BaseNavigationEvent.NavigateTo -> {
                    val route = buildRoute(navEvent.route, navEvent.dataBundle)
                    navController.navigate(route)
                }
                is BaseNavigationEvent.NavigateBackTo -> {
                    navController.popBackStack(navEvent.route, false)
                }
                is BaseNavigationEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        content(navController)
    }
}

private fun buildRoute(route: String, dataBundle: DataBundle): String {
    val encodedDataBundle = Uri.encode(Gson().toJson(dataBundle))
    return "$route/$encodedDataBundle"
}
