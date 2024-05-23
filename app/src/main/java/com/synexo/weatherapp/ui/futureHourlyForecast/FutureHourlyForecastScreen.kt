package com.synexo.weatherapp.ui.futureHourlyForecast

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.synexo.weatherapp.R
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.HourlyWeather
import com.synexo.weatherapp.ui.futureHourlyForecast.components.HourListItem
import com.synexo.weatherapp.ui.futureHourlyForecast.components.HumidityVicoLineChart
import com.synexo.weatherapp.ui.futureHourlyForecast.components.PressureVicoLineChart
import com.synexo.weatherapp.ui.futureHourlyForecast.components.RainVicoColumnChart
import com.synexo.weatherapp.ui.futureHourlyForecast.components.TempVicoLineChart
import com.synexo.weatherapp.ui.shared.components.ForecastTopBar
import com.synexo.weatherapp.ui.shared.viewModel.CityWeatherSharedViewModel
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme
import com.synexo.weatherapp.util.MockDataHelper
import com.synexo.weatherapp.util.extensions.DAY_DATE_MONTH_PATTERN
import com.synexo.weatherapp.util.extensions.rememberViewEvent
import com.synexo.weatherapp.util.extensions.toReadableDate

@Composable
fun FutureHourlyForecastScreen(
    navController: NavController,
    sharedStateViewModel: CityWeatherSharedViewModel,
    viewModel: FutureHourlyForecastScreenViewModel = hiltViewModel(),
) {
    val cityWeather by sharedStateViewModel.sharedState.collectAsState()
    val state by viewModel.viewState.collectAsState()
    val navigationEvent by viewModel.viewModelEvent.collectAsState()

    navigationEvent
        ?.getContentIfNotHandled()
        ?.let { event ->
            when (event) {
                is FutureHourlyForecastScreenViewModelEvent.NavigateBack -> navController.navigateUp()
            }
        }

    LaunchedEffect(Unit) {
        viewModel.onViewEvent(FutureHourlyForecastScreenViewEvent.SetHourlyWeatherData(cityWeather!!.weather))
    }

    FutureHourlyForecastScreenContent(
        state = state,
        onEvent = rememberViewEvent(viewModel = viewModel)
    )
}

@Composable
fun FutureHourlyForecastScreenContent(
    state: FutureHourlyForecastScreenState,
    onEvent: (FutureHourlyForecastScreenViewEvent) -> Unit
) {
    Scaffold(
        topBar = {
            ForecastTopBar(
                title = R.string.future_hourly_forecast_screen_title,
                onBackClick = {
                    onEvent(FutureHourlyForecastScreenViewEvent.OnBackClick)
                },
                onIconClick = {
                    onEvent(FutureHourlyForecastScreenViewEvent.SwitchViewType)
                },
                iconRes = when (state.hourlyForecastViewType) {
                    HourlyForecastViewType.GRAPH -> R.drawable.ic_list
                    HourlyForecastViewType.LIST -> R.drawable.ic_chart
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (state.hourlyForecastViewType) {
                HourlyForecastViewType.GRAPH -> GraphHourlyView(state)
                HourlyForecastViewType.LIST -> ListHourlyView(state)
            }
        }
    }
}

@Composable
private fun GraphHourlyView(
    state: FutureHourlyForecastScreenState,
    modifier: Modifier = Modifier
) {
    // TODO: Report the issue with infinite loop of updates in the charts
    /* Passing the same scroll state to multiple charts causes the charts to scroll together
     which is desired behavior but even without scrolling, creates infinite loop of updates in the charts*/
    // val scrollState = rememberVicoScrollState()
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 10.dp)
    ) {
        TempVicoLineChart(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            title = stringResource(id = R.string.common_temperature),
            state = state,
        )

        PressureVicoLineChart(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            title = stringResource(id = R.string.common_pressure),
            state = state,
        )

        HumidityVicoLineChart(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            title = stringResource(id = R.string.common_humidity),
            state = state,
        )

        RainVicoColumnChart(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            title = stringResource(id = R.string.common_rain),
            state = state,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ListHourlyView(
    state: FutureHourlyForecastScreenState,
    modifier: Modifier = Modifier
) {
    val splitList = splitItemsByDay(items = state.hourlyData)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        splitList
            .forEach { (day, dayData) ->
                stickyHeader {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LocalCustomColorsPalette.current.cardBackgroundGradient)
                            .padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = day,
                            fontSize = 14.sp
                        )
                    }
                }
                items(
                    items = dayData,
                    key = { it.timestamp }
                ) { item ->
                    HourListItem(hourlyWeather = item)
                }
            }
    }
}

@Composable
fun splitItemsByDay(items: List<HourlyWeather>): Map<String, List<HourlyWeather>> {
    return items.groupBy {
        it.getTimestamp().toReadableDate(DAY_DATE_MONTH_PATTERN)
    }
}

@PreviewLightDark
@Composable
fun FutureHourlyScreenPreview(
    @PreviewParameter(FutureHourlyForecastScreenParameterProvider::class) state: FutureHourlyForecastScreenState
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            FutureHourlyForecastScreenContent(
                state = state
            ) {}
        }
    }
}

private class FutureHourlyForecastScreenParameterProvider :
    PreviewParameterProvider<FutureHourlyForecastScreenState> {
    private fun createGraphState(): FutureHourlyForecastScreenState {
        val labelListKey: ExtraStore.Key<List<String>> = ExtraStore.Key()
        val xLabels: List<String> = listOf("00:00", "02:00", "03:00", "04:00", "05:00", "06:00")
        val humidityModel = CartesianChartModelProducer.build {
            lineSeries { series(70, 80, 40, 50, 90, 85) }
            updateExtras { it[labelListKey] = xLabels }
        }
        val tempModel = CartesianChartModelProducer.build {
            lineSeries { series(1, 2, 4, 3, 2, 3) }
            updateExtras { it[labelListKey] = xLabels }
        }
        val rainModel = CartesianChartModelProducer.build {
            columnSeries { series(1, 2, 4, 3, 2, 3) }
            updateExtras { it[labelListKey] = xLabels }
        }
        val pressureModel = CartesianChartModelProducer.build {
            lineSeries { series(5, 3, 1, 3, 2, 3) }
            updateExtras { it[labelListKey] = xLabels }
        }
        return FutureHourlyForecastScreenState(
            minTemp = 0f,
            maxTemp = 10f,
            minPressure = 0f,
            maxPressure = 10f,
            minRain = 0f,
            maxRain = 10f,
            labelListKey = labelListKey,
            weatherUnits = WeatherUnits(),
            humidityModelProducer = humidityModel,
            tempModelProducer = tempModel,
            rainModelProducer = rainModel,
            pressureModelProducer = pressureModel
        )
    }

    override val values: Sequence<FutureHourlyForecastScreenState>
        get() = sequenceOf(
            // Graphs are glitching, often not displaying any data
            createGraphState(),
            FutureHourlyForecastScreenState(
                hourlyForecastViewType = HourlyForecastViewType.LIST,
                hourlyData = listOf(
                    MockDataHelper.createHourlyWeather(),
                    MockDataHelper.createHourlyWeather()
                )
            )
        )
}
