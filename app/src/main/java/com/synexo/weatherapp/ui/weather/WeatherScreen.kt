@file:OptIn(ExperimentalFoundationApi::class)

package com.synexo.weatherapp.ui.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.synexo.weatherapp.domain.model.CityWeather
import com.synexo.weatherapp.ui.shared.components.CenteredProgress
import com.synexo.weatherapp.ui.shared.components.CityWeather
import com.synexo.weatherapp.ui.shared.components.ErrorDialog
import com.synexo.weatherapp.ui.shared.components.PullToRefreshCompose
import com.synexo.weatherapp.ui.shared.viewModel.CityWeatherSharedViewModel
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme
import com.synexo.weatherapp.ui.weather.components.NoSavedLocations
import com.synexo.weatherapp.ui.weather.components.WeatherTopBar
import com.synexo.weatherapp.util.MockDataHelper
import com.synexo.weatherapp.util.NavRoutes
import com.synexo.weatherapp.util.extensions.rememberViewEvent
import com.synexo.weatherapp.util.extensions.safeNavigate

@Composable
fun WeatherScreen(
    navController: NavController,
    sharedStateViewModel: CityWeatherSharedViewModel,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val navigationEvent by viewModel.viewModelEvent.collectAsState()

    navigationEvent
        ?.getContentIfNotHandled()
        ?.let { event ->
            when (event) {
                is WeatherScreenViewModelEvent.GoToFutureDailyForecastScreen -> {
                    sharedStateViewModel.updateState(event.cityWeather)
                    navController.safeNavigate(
                        NavRoutes.FutureDailyForecastScreen.createRoute(
                            NavRoutes.WeatherScreen,
                            event.index
                        )
                    )
                }

                is WeatherScreenViewModelEvent.GoToFutureHourlyForecastScreen -> {
                    sharedStateViewModel.updateState(event.cityWeather)
                    navController.safeNavigate(
                        NavRoutes.FutureHourlyForecastScreen.createRoute(
                            NavRoutes.WeatherScreen
                        )
                    )
                }

                WeatherScreenViewModelEvent.GoToMyLocationsScreen -> {
                    navController.safeNavigate(NavRoutes.MyLocationsScreen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            }
        }


    WeatherScreenContent(
        state = state,
        onEvent = rememberViewEvent(viewModel)
    )
}

@Composable
fun WeatherScreenContent(
    state: WeatherScreenState,
    onEvent: (WeatherScreenViewEvent) -> Unit
) {
    when {
        state.isLoading -> { CenteredProgress() }
        state.locations.isEmpty() -> NoSavedLocations {
            onEvent(WeatherScreenViewEvent.OnNoDataButtonClick)
        }

        else -> {
            val pagerState = rememberPagerState(
                initialPage = state.initialLocationIndex,
                pageCount = { state.locations.size }
            )

            state.appError?.let {
                ErrorDialog(
                    appError = it,
                    onDialogClose = { onEvent(WeatherScreenViewEvent.DismissErrorDialog) }
                )
            }

            Scaffold { innerPadding ->
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState
                ) { page ->
                    SavedLocations(
                        modifier = Modifier.padding(innerPadding),
                        index = page,
                        isRefreshing = state.isRefreshing,
                        cityWeather = state.locations[page],
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun SavedLocations(
    index: Int,
    isRefreshing: Boolean,
    cityWeather: CityWeather,
    onEvent: (WeatherScreenViewEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshCompose(
        isRefreshing = isRefreshing,
        onRefresh = { onEvent(WeatherScreenViewEvent.RefreshWeatherData(index)) },
        content = {
            Scaffold(
                modifier = modifier
                    .fillMaxSize(),
                topBar = {
                    WeatherTopBar(
                        cityDetails = cityWeather.cityDetails
                    )
                },
            ) { innerPadding ->
                CityWeather(
                    modifier = Modifier.padding(innerPadding),
                    state = cityWeather,
                    onDailyItemClick = {
                        onEvent(WeatherScreenViewEvent.OnDailyItemClick(index, it))
                    },
                    onHourlyItemClick = {
                        onEvent(WeatherScreenViewEvent.OnHourlyItemClick(index))
                    },
                )
            }
        },
    )
}


@PreviewLightDark
@Composable
fun WeatherScreenPreview(
    @PreviewParameter(StateParameterProvider::class) state: WeatherScreenState
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            WeatherScreenContent(
                state = state,
                onEvent = {}
            )
        }
    }
}

private class StateParameterProvider : PreviewParameterProvider<WeatherScreenState> {
    override val values: Sequence<WeatherScreenState>
        get() = sequenceOf(
            WeatherScreenState(
                locations = listOf(MockDataHelper.createCityWeather())
            ),
            WeatherScreenState(
                locations = listOf()
            ),
            WeatherScreenState(
                isLoading = true
            )
        )
}
