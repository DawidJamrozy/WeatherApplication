package com.synexo.weatherapp.addCity.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.synexo.weatherapp.addCity.presentation.components.AddCityTopBar
import com.synexo.weatherapp.core.model.MockDataHelper
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.core.ui.components.CityWeather
import com.synexo.weatherapp.core.ui.safeNavigate
import com.synexo.weatherapp.core.ui.util.NavRoutes
import com.synexo.weatherapp.core.ui.viewModel.CityWeatherSharedViewModel
import com.synexo.weatherapp.core.ui.viewModel.rememberViewEvent

@Composable
fun AddCityWeatherScreen(
    navController: NavController,
    sharedStateViewModel: CityWeatherSharedViewModel,
    viewModel: AddCityWeatherViewModel = hiltViewModel()
) {
    val sharedState by sharedStateViewModel.sharedState.collectAsState()
    val navigationEvent by viewModel.viewModelEvent.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onViewEvent(AddCityScreenViewEvent.SetCityWeather(sharedState!!))
    }

    val state by viewModel.viewState.collectAsState()

    navigationEvent
        ?.getContentIfNotHandled()
        ?.let { event ->
            when (event) {
                is AddCityScreenViewModelEvent.NavigateBack -> {
                    navController.navigateUp()
                }

                is AddCityScreenViewModelEvent.CityAddSuccess -> {
                    navController.safeNavigate(NavRoutes.MyLocationsScreen.route) {
                        popUpTo(NavRoutes.MyLocationsScreen.route) {
                            inclusive = true
                        }
                    }
                }

                is AddCityScreenViewModelEvent.GoToFutureDailyForecastScreen -> {
                    navController.safeNavigate(
                        NavRoutes.FutureDailyForecastScreen.createRoute(
                            NavRoutes.AddCityWeatherScreen,
                            event.index
                        )
                    )
                }

                is AddCityScreenViewModelEvent.GoToFutureHourlyForecastScreen -> {
                    navController.safeNavigate(
                        NavRoutes.FutureHourlyForecastScreen.createRoute(
                            NavRoutes.AddCityWeatherScreen
                        )
                    )
                }
            }
        }

    if (state.cityWeather == null) return

    AddCityWeatherScreenContent(
        state = state,
        onEvent = rememberViewEvent(viewModel)
    )
}

@Composable
fun AddCityWeatherScreenContent(
    state: AddCityScreenState,
    onEvent: (AddCityScreenViewEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AddCityTopBar(
                cityDetails = state.cityWeather!!.cityDetails,
                onEvent = onEvent
            )
        }
    ) { innerPadding ->
        CityWeather(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            state = state.cityWeather!!,
            onDailyItemClick = { onEvent(AddCityScreenViewEvent.OnDailyItemClick(it)) },
            onHourlyItemClick = { onEvent(AddCityScreenViewEvent.OnHourlyItemClick) }
        )
    }
}

@PreviewLightDark
@Composable
fun AddCityWeatherScreenPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            AddCityWeatherScreenContent(
                state = AddCityScreenState(MockDataHelper.createCityWeather()),
                onEvent = {}
            )
        }
    }
}