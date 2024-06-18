package com.synexo.weatherapp.futureDailyForecast.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.model.weather.DailyWeather
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.components.WeatherDetailItem
import com.synexo.weatherapp.core.ui.util.getDayPeriodSummaryItems
import com.synexo.weatherapp.core.ui.util.toDayWeatherConditionsItems
import com.synexo.weatherapp.futureDailyForecast.presentation.util.gridItems

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
