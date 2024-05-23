package com.synexo.weatherapp.util.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.safeNavigate(destination: String) {
    val currentDestination = currentBackStackEntry?.destination?.route
    if (destination != currentDestination) {
        navigate(destination)
    }
}

fun NavController.safeNavigate(destination: String, builder: NavOptionsBuilder.() -> Unit) {
    val currentDestination = currentBackStackEntry?.destination?.route
    if (destination != currentDestination) {
        navigate(destination, builder)
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry  = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}
