package com.synexo.weatherapp.futureDailyForecast.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.core.ui.components.CenteredProgress
import com.synexo.weatherapp.core.ui.components.ForecastTopBar
import com.synexo.weatherapp.core.ui.components.WeatherDetailItem
import com.synexo.weatherapp.core.ui.viewModel.CityWeatherSharedViewModel
import com.synexo.weatherapp.core.ui.viewModel.rememberViewEvent
import com.synexo.weatherapp.futureDailyForecast.presentation.components.DailyDetailsSummary
import com.synexo.weatherapp.futureDailyForecast.presentation.components.SelectableDateWidget
import com.synexo.weatherapp.futureDailyForecast.presentation.components.WeatherAbbreviationsDialog
import com.synexo.weatherapp.futureDailyForecast.presentation.util.gridItems
import kotlinx.coroutines.launch
import com.synexo.weatherapp.core.ui.R as CoreR

@Composable
fun FutureDailyForecastScreen(
    navController: NavController,
    sharedStateViewModel: CityWeatherSharedViewModel,
    viewModel: FutureDailyForecastScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val sharedState by sharedStateViewModel.sharedState.collectAsState()
    val navigationEvent by viewModel.viewModelEvent.collectAsState()

    navigationEvent
        ?.getContentIfNotHandled()
        ?.let { event ->
            when (event) {
                is FutureDailyForecastScreenViewModelEvent.NavigateBack -> {
                    navController.navigateUp()
                }
            }
        }

    LaunchedEffect(Unit) {
        viewModel.onViewEvent(FutureDailyForecastScreenViewEvent.SetCityWeather(sharedState!!))
    }

    FutureDailyForecastScreenContent(
        state = state,
        event = rememberViewEvent(viewModel)
    )
}

@Composable
fun FutureDailyForecastScreenContent(
    state: FutureDailyForecastScreenState,
    event: (FutureDailyForecastScreenViewEvent) -> Unit
) {
    when {
        state.isLoading -> {
            CenteredProgress()
        }

        state.cityWeather != null -> {
            Scaffold(
                topBar = {
                    ForecastTopBar(
                        title = R.string.future_daily_forecast_screen_title,
                        onBackClick = {
                            event(FutureDailyForecastScreenViewEvent.OnBackClick)
                        },
                        onIconClick = {
                            event(FutureDailyForecastScreenViewEvent.SetAbbreviatedDialogState(true))
                        },
                        iconRes = R.drawable.ic_info
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    val pagerState = rememberPagerState(
                        initialPage = state.initialDayIndex,
                        pageCount = { state.cityWeather.weather.dailyWeather.size }
                    )


                    if (state.abbreviatedDialogVisible) {
                        WeatherAbbreviationsDialog(
                            onDialogClose = {
                                event(
                                    FutureDailyForecastScreenViewEvent.SetAbbreviatedDialogState(
                                        false
                                    )
                                )
                            }
                        )
                    }

                    ScrollableTabRow(
                        containerColor = LocalCustomColorsPalette.current.background,
                        selectedTabIndex = pagerState.currentPage,
                        edgePadding = 0.dp,
                        indicator = {},
                        tabs = {
                            state
                                .cityWeather
                                .weather
                                .dailyWeather
                                .forEachIndexed { index, daily ->
                                    SelectableDateWidget(
                                        isSelected = pagerState.currentPage == index,
                                        time = daily.getTimestamp(),
                                        index = index,
                                        onClick = {
                                            coroutineScope.launch {
                                                pagerState.scrollToPage(
                                                    page = it
                                                )
                                            }
                                        }
                                    )
                                }
                        }
                    )

                    HorizontalPager(
                        modifier = Modifier.fillMaxSize(),
                        state = pagerState
                    ) { page ->
                        DailyDetailsSummary(
                            dailyWeather = state.cityWeather.weather.dailyWeather[page],
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun FutureDailyForecastScreenContentPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            FutureDailyForecastScreenContent(
                state = FutureDailyForecastScreenState(),
                event = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
fun MyGridPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            LazyColumn {
                gridItems(
                    data = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                    columnCount = 2,
                    modifier = Modifier.fillMaxWidth(),
                ) { item ->
                    WeatherDetailItem(
                        icon = CoreR.drawable.ic_sunrise,
                        title = "Feels Like",
                        text = item
                    )
                }
            }
        }
    }
}