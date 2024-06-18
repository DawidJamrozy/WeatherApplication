package com.synexo.weatherapp.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.synexo.weatherapp.addCity.presentation.AddCityWeatherScreen
import com.synexo.weatherapp.core.ui.LocalSharedTransitionScope
import com.synexo.weatherapp.core.ui.util.NavRoutes
import com.synexo.weatherapp.core.ui.viewModel.CityWeatherSharedViewModel
import com.synexo.weatherapp.futureDailyForecast.presentation.FutureDailyForecastScreen
import com.synexo.weatherapp.futureHourlyForecast.presentation.FutureHourlyForecastScreen
import com.synexo.weatherapp.myLocations.presentation.MyLocationsScreen
import com.synexo.weatherapp.search.presentation.SearchScreen
import com.synexo.weatherapp.settings.presentation.SettingsScreen
import com.synexo.weatherapp.util.composableWithCompositionLocal
import com.synexo.weatherapp.util.sharedViewModel
import com.synexo.weatherapp.weather.presentation.WeatherScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (NavRoutes.shouldDisplayBottomBar(currentRoute)) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        SharedTransitionLayout {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this,
            ) {
                Box(modifier = Modifier.padding(innerPadding)) {
                    NavHost(
                        navController = navController,
                        startDestination = NavRoutes.PREVIEW_CITIES_ROUTE
                    ) {
                        composable(NavRoutes.SettingsScreen.route) {
                            SettingsScreen()
                        }

                        navigation(
                            startDestination = NavRoutes.MyLocationsScreen.route,
                            route = NavRoutes.ADD_NEW_CITY_ROUTE
                        ) {
                            composableWithCompositionLocal(
                                route = NavRoutes.MyLocationsScreen.route,
                            ) { entry ->
                                val viewModel = entry
                                    .sharedViewModel<CityWeatherSharedViewModel>(navController)
                                val cityName = entry.savedStateHandle.get<String>("resultKey")
                                MyLocationsScreen(
                                    navController = navController,
                                    sharedStateViewModel = viewModel,
                                    cityName = cityName
                                )
                            }

                            composableWithCompositionLocal(NavRoutes.SearchScreen.route) { entry ->
                                val viewModel = entry
                                    .sharedViewModel<CityWeatherSharedViewModel>(navController)
                                SearchScreen(
                                    sharedStateViewModel = viewModel,
                                    navController = navController
                                )
                            }

                            composable(NavRoutes.AddCityWeatherScreen.route) { entry ->
                                val viewModel = entry
                                    .sharedViewModel<CityWeatherSharedViewModel>(navController)
                                AddCityWeatherScreen(
                                    navController = navController,
                                    sharedStateViewModel = viewModel,
                                )
                            }

                            composable(
                                route = NavRoutes.FutureDailyForecastScreen.getRoute(NavRoutes.AddCityWeatherScreen),
                                arguments = listOf(navArgument(NavRoutes.DAILY_INDEX_KEY) {
                                    type = NavType.IntType
                                })
                            ) { entry ->
                                val viewModel = entry
                                    .sharedViewModel<CityWeatherSharedViewModel>(navController)
                                FutureDailyForecastScreen(
                                    navController = navController,
                                    sharedStateViewModel = viewModel
                                )
                            }

                            composable(
                                route = NavRoutes.FutureHourlyForecastScreen.getRoute(NavRoutes.AddCityWeatherScreen),
                            ) { entry ->
                                val viewModel =
                                    entry.sharedViewModel<CityWeatherSharedViewModel>(navController)
                                FutureHourlyForecastScreen(
                                    navController = navController,
                                    sharedStateViewModel = viewModel
                                )
                            }
                        }

                        navigation(
                            startDestination = NavRoutes.WeatherScreen.route,
                            route = NavRoutes.PREVIEW_CITIES_ROUTE
                        ) {
                            composable(
                                route = NavRoutes.WeatherScreen.route,
                                arguments = listOf(navArgument(NavRoutes.CITY_INDEX_KEY) {
                                    type = NavType.IntType
                                    defaultValue = 0
                                })
                            ) { entry ->
                                val viewModel = entry
                                    .sharedViewModel<CityWeatherSharedViewModel>(navController)
                                WeatherScreen(
                                    navController = navController,
                                    sharedStateViewModel = viewModel
                                )
                            }

                            composable(
                                route = NavRoutes.FutureDailyForecastScreen.getRoute(NavRoutes.WeatherScreen),
                                arguments = listOf(navArgument(NavRoutes.DAILY_INDEX_KEY) {
                                    type = NavType.IntType
                                })
                            ) { entry ->
                                val viewModel = entry
                                    .sharedViewModel<CityWeatherSharedViewModel>(navController)
                                FutureDailyForecastScreen(
                                    navController = navController,
                                    sharedStateViewModel = viewModel
                                )
                            }

                            composable(
                                route = NavRoutes.FutureHourlyForecastScreen.getRoute(NavRoutes.WeatherScreen),
                            ) { entry ->
                                val viewModel =
                                    entry.sharedViewModel<CityWeatherSharedViewModel>(navController)
                                FutureHourlyForecastScreen(
                                    navController = navController,
                                    sharedStateViewModel = viewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
