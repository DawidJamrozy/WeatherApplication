package com.synexo.weatherapp.futureHourlyForecast.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.model.MockDataHelper
import com.synexo.weatherapp.core.model.weather.HourlyWeather
import com.synexo.weatherapp.core.ui.DAY_DATE_HOUR_MINUTE_PATTERN
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.core.ui.components.WeatherDetailItem
import com.synexo.weatherapp.core.ui.toDate
import com.synexo.weatherapp.core.ui.util.getIconResId
import com.synexo.weatherapp.core.ui.util.toDayWeatherConditionsItems
import com.synexo.weatherapp.core.ui.R as CoreR

@Composable
fun HourListItem(
    hourlyWeather: HourlyWeather,
) {
    var visible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { visible = !visible }
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(LocalCustomColorsPalette.current.cardBackgroundGradient),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = hourlyWeather.details.getIconResId()),
                        contentDescription = null
                    )
                }
            }


            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = hourlyWeather
                        .getTimestamp()
                        .toDate(DAY_DATE_HOUR_MINUTE_PATTERN)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                    fontSize = 11.sp,
                    lineHeight = 11.sp,
                    color = LocalCustomColorsPalette.current.text
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = hourlyWeather.details.description.replaceFirstChar { it.uppercase() },
                    lineHeight = 10.sp,
                    fontSize = 10.sp,
                    color = LocalCustomColorsPalette.current.textSecondary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = CoreR.drawable.ic_temp),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = hourlyWeather.temp.getValueWithUnit(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(end = 16.dp)
                )

                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = CoreR.drawable.ic_feels_like_temp),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = hourlyWeather.feelsLike.getValueWithUnit(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        AnimatedVisibility(
            visible = visible
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                gridItems(
                    modifier = Modifier.fillMaxWidth(),
                    data = hourlyWeather.toDayWeatherConditionsItems(),
                    columnCount = 3,
                ) { item ->
                    WeatherDetailItem(
                        modifier = Modifier.padding(vertical = 4.dp),
                        icon = item.icon,
                        title = stringResource(id = item.title),
                        text = item.text
                    )
                }
            }
        }
    }
}

@Composable
private fun <T> gridItems(
    modifier: Modifier,
    data: List<T>,
    columnCount: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    (0 until rows).forEach { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true,
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun HourListItemPreview(
) {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            HourListItem(
                hourlyWeather = MockDataHelper.createHourlyWeather()
            )
        }
    }
}