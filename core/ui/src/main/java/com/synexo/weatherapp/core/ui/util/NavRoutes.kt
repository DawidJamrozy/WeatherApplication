package com.synexo.weatherapp.core.ui.util

sealed class NavRoutes(val route: String) {

    companion object {
        // Main - BottomNav
        private const val WEATHER_SCREEN = "_weather_screen"
        private const val MY_LOCATIONS = "_my_locations"
        private const val SETTINGS_SCREEN = "_settings_screen"

        // Side
        private const val ADD_CITY_WEATHER_SCREEN = "add_city_weather_screen"
        private const val SEARCH_SCREEN = "search_screen"
        private const val FUTURE_DAILY_FORECAST_SCREEN = "future_daily_forecast_screen"
        private const val FUTURE_HOURLY_FORECAST_SCREEN = "future_hourly_forecast_screen"

        const val ADD_NEW_CITY_ROUTE = "add_new_city"
        const val PREVIEW_CITIES_ROUTE = "preview_cities"

        const val DAILY_INDEX_KEY = "daily_index"
        const val CITY_INDEX_KEY = "city_index"

        fun shouldDisplayBottomBar(route: String?): Boolean {
            return when  {
                route?.startsWith("_") == true -> true
                else -> false
            }
        }
    }

    data object MyLocationsScreen : NavRoutes(MY_LOCATIONS)
    data object WeatherScreen : NavRoutes("${WEATHER_SCREEN}/{$CITY_INDEX_KEY}") {
        fun createRoute(cityId: Int) = "$WEATHER_SCREEN/$cityId"
    }

    data object SettingsScreen : NavRoutes(SETTINGS_SCREEN)

    data object AddCityWeatherScreen : NavRoutes(ADD_CITY_WEATHER_SCREEN)

    data object SearchScreen : NavRoutes(SEARCH_SCREEN)

    data object FutureDailyForecastScreen : NavRoutes(FUTURE_DAILY_FORECAST_SCREEN) {

        fun getRoute(from: NavRoutes): String = "${route}-${from.route}/{$DAILY_INDEX_KEY}"

        fun createRoute(from: NavRoutes, dailyIndex: Int) = "${route}-${from.route}/$dailyIndex"
    }

    data object FutureHourlyForecastScreen : NavRoutes(FUTURE_HOURLY_FORECAST_SCREEN) {

        fun getRoute(from: NavRoutes): String = "${route}-${from.route}"

        fun createRoute(from: NavRoutes) = "${route}-${from.route}"
    }

}