package com.synexo.weatherapp.ui.futureDailyForecast

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.synexo.weatherapp.R
import com.synexo.weatherapp.domain.model.weather.DailyWeather
import com.synexo.weatherapp.domain.model.weather.Time
import com.synexo.weatherapp.ui.futureDailyForecast.components.DayPartTemp
import com.synexo.weatherapp.ui.futureDailyForecast.components.WeatherAbbreviationsDialog
import com.synexo.weatherapp.ui.shared.components.CenteredProgress
import com.synexo.weatherapp.ui.shared.components.ForecastTopBar
import com.synexo.weatherapp.ui.shared.components.WeatherDetailItem
import com.synexo.weatherapp.ui.shared.viewModel.CityWeatherSharedViewModel
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme
import com.synexo.weatherapp.util.extensions.getDayPeriodSummaryItems
import com.synexo.weatherapp.util.extensions.getLocalDate
import com.synexo.weatherapp.util.extensions.gridItems
import com.synexo.weatherapp.util.extensions.rememberViewEvent
import com.synexo.weatherapp.util.extensions.toDayWeatherConditionsItems
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import kotlin.math.min

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

@OptIn(ExperimentalFoundationApi::class)
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
                                    val (dayOfWeek, dayOfMonth) = timeToDayOfWeekAndDayOfMonth(daily.getTimestamp())

                                    SelectableDateWidget(
                                        isSelected = pagerState.currentPage == index,
                                        date = dayOfMonth,
                                        dayOfWeek = dayOfWeek,
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

@Composable
fun DailyDetailsSummary(
    dailyWeather: DailyWeather,
) {
    val dailyItems = dailyWeather.toDayWeatherConditionsItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(dailyWeather.getDayPeriodSummaryItems()) { item ->
            DayPartTemp(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                timeOfDay = item.timeOfDay,
                temperature = item.temperature,
                feelsLikeTemperature = item.feelsLikeTemperature,
            )

            HorizontalDivider(
                color = LocalCustomColorsPalette.current.icon.copy(0.5f)
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        gridItems(
            modifier = Modifier.fillMaxWidth(),
            data = dailyItems,
            columnCount = 2,
        ) { item ->
            WeatherDetailItem(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                icon = item.icon,
                title = stringResource(id = item.title),
                text = item.text
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = LocalCustomColorsPalette.current.icon.copy(0.5f)
            )
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = dailyWeather.details.description.replaceFirstChar { it.uppercase() },
                fontSize = 14.sp,
                color = LocalCustomColorsPalette.current.text
            )
        }
    }
}

@Composable

fun SelectableDateWidget(
    isSelected: Boolean,
    date: String,
    dayOfWeek: String,
    index: Int,
    onClick: (Int) -> Unit,
) {
    val (background, text) = with(LocalCustomColorsPalette.current) {
        if (isSelected)
            buttonBackground to textReverse
        else
            background to text
    }

    val backgroundColor by animateColorAsState(background)
    val textColor by animateColorAsState(text)
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick(index) }
            .padding(8.dp)
            .wrapContentWidth()
            .background(shape = RoundedCornerShape(8.dp), color = backgroundColor)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = dayOfWeek,
            color = textColor,
            fontSize = 10.sp,
            lineHeight = 10.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = date,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            lineHeight = 10.sp
        )
    }
}

private fun timeToDayOfWeekAndDayOfMonth(
    time: Time,
): Pair<String, String> {
    return time.getLocalDate()
        .format(DateTimeFormatter.ofPattern("E d"))
        .run {
            if (contains(".")) {
                replace(" ", "").split(".")
            } else {
                split(" ")
            }
        }
        .let { (dayOfWeek, dayOfMonth) ->
            val formattedDayOfWeek = dayOfWeek
                .substring(0, min(3, dayOfWeek.length))
                .replaceFirstChar { it.uppercase() }
            formattedDayOfWeek to dayOfMonth
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

@Preview

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
                        icon = R.drawable.ic_sunrise,
                        title = "Feels Like",
                        text = item
                    )
                }
            }
        }
    }
}