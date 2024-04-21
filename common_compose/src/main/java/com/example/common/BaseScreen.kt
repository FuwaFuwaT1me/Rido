package com.example.common

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.common.navigation.DataBundle
import com.example.core.mvi.api.Action
import com.example.core.mvi.api.Event
import com.example.core.mvi.api.MviNavigationEvent
import com.example.core.mvi.api.State
import com.example.core.mvi.impl.BaseNavigationEvent
import com.example.core.mvi.impl.BaseViewModel
import com.google.gson.Gson

@Composable
fun BaseScreen(
    navController: NavController,
    viewModel: BaseViewModel<out Action, out Event, out State, out MviNavigationEvent>,
    content: @Composable () -> Unit
) {
    BackHandler {
        navController.navigateUp()
    }

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

    content()
}

private fun buildRoute(route: String, dataBundle: DataBundle): String {
    val encodedDataBundle = Uri.encode(Gson().toJson(dataBundle))
    return "$route/$encodedDataBundle"
}
