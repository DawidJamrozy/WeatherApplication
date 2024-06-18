package com.synexo.weatherapp.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.model.CityWeather
import com.synexo.weatherapp.core.model.MockDataHelper
import com.synexo.weatherapp.core.model.weather.DailyWeather
import com.synexo.weatherapp.core.model.weather.HourlyWeather
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.R
import com.synexo.weatherapp.core.ui.WeatherAppTheme

@Composable
fun CityWeather(
    state: CityWeather,
    onDailyItemClick: (Int) -> Unit,
    onHourlyItemClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        CurrentWeatherCard(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            state = state.weather.currentWeather,
            dailyWeather = state.weather.dailyWeather.first()
        )

        HourlyInfoList(
            modifier = Modifier.padding(bottom = 10.dp),
            hourlyWeather = state.weather.hourlyWeather,
        )

        DailyInfoList(
            dailyWeatherList = state.weather.dailyWeather,
            onItemClick = onDailyItemClick,
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 5.dp)
        )

        DetailsItem(
            icon = Icons.Filled.Search,
            text = stringResource(id = R.string.future_hourly_forecast),
            modifier = Modifier
                .clickable { onHourlyItemClick() }
                .padding(horizontal = 10.dp, vertical = 5.dp),
        )

        DetailsItem(
            icon = Icons.Filled.Search,
            text = stringResource(id = R.string.future_daily_forecast),
            modifier = Modifier
                .clickable { onDailyItemClick(0) }
                .padding(horizontal = 10.dp, vertical = 5.dp),
        )
    }
}

@Composable
fun HourlyInfoList(
    hourlyWeather: List<HourlyWeather>,
    modifier: Modifier = Modifier
) {
    val list = hourlyWeather.subList(0, 24)
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        items(
            count = list.size,
            key = { list[it].timestamp }
        ) { index ->
            HourlyCard(
                hourlyWeather = list[index],
                modifier = Modifier.padding(end = if (index < list.size - 1) 10.dp else 0.dp)
            )
        }
    }
}


@Composable
fun DailyInfoList(
    dailyWeatherList: List<DailyWeather>,
    onItemClick: (Int) -> Unit,
) {
    Column() {
        dailyWeatherList.forEachIndexed { index, daily ->
            DailyCard(
                dailyWeather = daily,
                modifier = Modifier
                    .clickable { onItemClick(index) }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
    }
}

@Composable
fun DetailsItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .size(40.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalCustomColorsPalette.current.cardBackgroundGradient),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterVertically),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            text = text,
            color = LocalCustomColorsPalette.current.text,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            modifier = Modifier
                .size(12.dp)
                .align(Alignment.CenterVertically),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            tint = LocalCustomColorsPalette.current.icon,
            contentDescription = null
        )
    }

}

@PreviewLightDark
@Composable
fun CityWeatherPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            CityWeather(
                state = MockDataHelper.createCityWeather(),
                onDailyItemClick = {}
            )
        }
    }
}