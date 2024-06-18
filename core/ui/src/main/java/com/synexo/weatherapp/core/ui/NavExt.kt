package com.synexo.weatherapp.core.ui

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