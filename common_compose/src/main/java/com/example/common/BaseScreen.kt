package com.example.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.core.mvi.api.Action
import com.example.core.mvi.api.NavigationEvent
import com.example.core.mvi.api.State
import com.example.core.mvi.impl.BaseNavigationEvent
import com.example.core.mvi.impl.BaseViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    navController: NavController,
    viewModel: BaseViewModel<out Action, out State, out NavigationEvent>,
    content: @Composable (NavController) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.model.navigationEvent.collect { navEvent ->
            when (navEvent) {
                is BaseNavigationEvent.NavigateTo -> {
                    navController.navigate(navEvent.screen)
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
