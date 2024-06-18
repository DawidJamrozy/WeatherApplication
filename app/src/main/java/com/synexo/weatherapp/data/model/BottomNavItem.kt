package com.synexo.weatherapp.data.model

import com.synexo.weatherapp.R
import com.synexo.weatherapp.core.ui.util.NavRoutes
import com.synexo.weatherapp.core.ui.R as CoreR

sealed class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: Int
) {
    data object MyLocations : BottomNavItem(
        route = NavRoutes.ADD_NEW_CITY_ROUTE,
        icon = R.drawable.ic_location_nav_item,
        label = R.string.bottom_nav_item_my_locations
    )

    data object Weather : BottomNavItem(
        route = NavRoutes.PREVIEW_CITIES_ROUTE,
        icon = CoreR.drawable.ic_clouds,
        label = R.string.bottom_nav_item_weather
    )

    data object Settings : BottomNavItem(
        route = NavRoutes.SettingsScreen.route,
        icon = R.drawable.ic_settings_nav_item,
        label = R.string.bottom_nav_item_settings
    )

}