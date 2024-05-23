package com.synexo.weatherapp.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.synexo.weatherapp.data.model.BottomNavItem
import com.synexo.weatherapp.domain.model.Theme
import com.synexo.weatherapp.domain.usecase.GetAppSettingsUseCase
import com.synexo.weatherapp.domain.usecase.RefreshAllWeatherDataUseCase
import com.synexo.weatherapp.ui.addCity.AddCityWeatherScreen
import com.synexo.weatherapp.ui.futureDailyForecast.FutureDailyForecastScreen
import com.synexo.weatherapp.ui.futureHourlyForecast.FutureHourlyForecastScreen
import com.synexo.weatherapp.ui.myLocations.MyLocationsScreen
import com.synexo.weatherapp.ui.settings.SettingsScreen
import com.synexo.weatherapp.ui.shared.viewModel.CityWeatherSharedViewModel
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme
import com.synexo.weatherapp.ui.weather.WeatherScreen
import com.synexo.weatherapp.util.NavRoutes
import com.synexo.weatherapp.util.extensions.safeNavigate
import com.synexo.weatherapp.util.extensions.sharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getAppSettingsUseCase: GetAppSettingsUseCase

    @Inject
    lateinit var refreshAllWeatherDataUseCase: RefreshAllWeatherDataUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disableTabRowMinWidth(this)
        refreshWeatherData()
        setContent {
            val resource by getAppSettingsUseCase
                .invoke(Unit)
                .collectAsState(initial = null)

            resource
                ?.appSettings
                ?.let { weatherSettings ->
                    val darkTheme = when (weatherSettings.theme) {
                        is Theme.Light -> false
                        is Theme.Dark -> true
                        is Theme.System -> isSystemInDarkTheme()
                    }

                    WeatherAppTheme(darkTheme, false) {
                        MyApp()
                    }
                }
        }
    }

    private fun refreshWeatherData(){
        lifecycleScope.launch {
            refreshAllWeatherDataUseCase.invoke(Unit)
        }
    }
}

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
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = NavRoutes.ADD_NEW_CITY_ROUTE
            ) {

                composable(NavRoutes.SettingsScreen.route) {
                    SettingsScreen()
                }

                navigation(
                    startDestination = NavRoutes.MyLocationsScreen.route,
                    route = NavRoutes.ADD_NEW_CITY_ROUTE
                ) {
                    composable(NavRoutes.MyLocationsScreen.route) { entry ->
                        val viewModel = entry
                            .sharedViewModel<CityWeatherSharedViewModel>(navController)
                        MyLocationsScreen(
                            navController = navController,
                            sharedStateViewModel = viewModel
                        )
                    }

                    composable(NavRoutes.AddCityWeatherScreen.route) { entry ->
                        val viewModel = entry
                            .sharedViewModel<CityWeatherSharedViewModel>(navController)
                        AddCityWeatherScreen(
                            navController = navController,
                            sharedStateViewModel = viewModel
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

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.MyLocations,
        BottomNavItem.Weather,
        BottomNavItem.Settings
    )

    NavigationBar(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        containerColor = LocalCustomColorsPalette.current.navigationBackground
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        items.forEach { item ->
            NavigationBarItem(
                selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == item.route } == true,
                colors = NavigationBarItemDefaults
                    .colors(
                        selectedIconColor = LocalCustomColorsPalette.current.bottomBarSelectedNavigationItem,
                        selectedTextColor = LocalCustomColorsPalette.current.bottomBarSelectedNavigationItem,
                        unselectedIconColor = LocalCustomColorsPalette.current.bottomBarUnselectedNavigationItem,
                        unselectedTextColor = LocalCustomColorsPalette.current.bottomBarUnselectedNavigationItem,
                        indicatorColor = LocalCustomColorsPalette.current.bottomBarSelectedNavigationItem.copy(alpha = 0.1f),
                    ),
                onClick = {
                    navController.safeNavigate(item.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.label),
                        fontSize = 12.sp
                    )
                }
            )
        }
    }
}

private fun disableTabRowMinWidth(context: Context) {
    try {
        Class
            .forName("androidx.compose.material3.TabRowKt")
            .getDeclaredField("ScrollableTabRowMinimumTabWidth").apply {
                isAccessible = true
            }.set(context, 0f)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


