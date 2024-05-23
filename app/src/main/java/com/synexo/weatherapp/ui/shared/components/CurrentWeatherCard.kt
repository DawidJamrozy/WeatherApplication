package com.synexo.weatherapp.ui.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.synexo.weatherapp.R
import com.synexo.weatherapp.domain.model.weather.CurrentWeather
import com.synexo.weatherapp.domain.model.weather.DailyWeather
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme
import com.synexo.weatherapp.util.MockDataHelper
import com.synexo.weatherapp.util.extensions.DAY_DATE_MONTH_HOUR_MINUTE_PATTERN
import com.synexo.weatherapp.util.extensions.HOUR_MINUTE_PATTERN
import com.synexo.weatherapp.util.extensions.calculateDaylight
import com.synexo.weatherapp.util.extensions.convertWindDegreesToDirection
import com.synexo.weatherapp.util.extensions.getIconResId
import com.synexo.weatherapp.util.extensions.toDate

@Composable
fun CurrentWeatherCard(
    state: CurrentWeather,
    dailyWeather: DailyWeather,
    modifier: Modifier = Modifier,
) {
    var visible by remember { mutableStateOf(false) }
    val rotationDegree by animateFloatAsState(if (visible) 180f else 0f, label = "")
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { visible = !visible }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalCustomColorsPalette.current.cardBackgroundGradient)
                .padding(10.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (dateRef, tempRef, tempUnit, conditionRef, feelsLikeRef, iconRef) = createRefs()

                Text(
                    modifier = Modifier.constrainAs(dateRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(tempRef.top)
                    },
                    text = state
                        .getTimestamp()
                        .toDate(DAY_DATE_MONTH_HOUR_MINUTE_PATTERN)
                        .replaceFirstChar { it.uppercase() },
                    fontSize = 9.sp,
                    lineHeight = 18.sp,
                    color = LocalCustomColorsPalette.current.textSecondary
                )

                Text(
                    modifier = Modifier.constrainAs(tempRef) {
                        top.linkTo(dateRef.bottom)
                        start.linkTo(dateRef.start)
                        bottom.linkTo(conditionRef.top)
                    },
                    text = state.temp.getValue().toString(),
                    fontSize = 46.sp,
                    color = LocalCustomColorsPalette.current.text,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .constrainAs(tempUnit) {
                            top.linkTo(tempRef.top)
                            start.linkTo(tempRef.end)
                        },
                    text = state.temp.getUnit(),
                    color = LocalCustomColorsPalette.current.text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    modifier = Modifier.constrainAs(conditionRef) {
                        top.linkTo(tempRef.bottom)
                        start.linkTo(tempRef.start)
                        bottom.linkTo(feelsLikeRef.top)
                    },
                    text = state.details.description.replaceFirstChar { it.uppercase() },
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    color = LocalCustomColorsPalette.current.text
                )
                Text(
                    modifier = Modifier.constrainAs(feelsLikeRef) {
                        top.linkTo(conditionRef.bottom)
                        start.linkTo(conditionRef.start)
                        bottom.linkTo(parent.bottom)
                    },
                    text = stringResource(
                        id = R.string.common_feels_like,
                        state.feelsLike.getValueWithUnit()
                    ),
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    color = LocalCustomColorsPalette.current.text
                )

                createVerticalChain(
                    dateRef,
                    tempRef,
                    conditionRef,
                    feelsLikeRef,
                    chainStyle = ChainStyle.Packed
                )

                Image(
                    modifier = Modifier
                        .constrainAs(iconRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .size(100.dp),
                    painter = painterResource(id = state.details.getIconResId()),
                    contentDescription = null,
                )
            }

            AnimatedVisibility(
                visible = visible
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    InfoColumn(
                        topTitle = stringResource(id = R.string.common_sunrise),
                        topText = state.getSunrise().toDate(HOUR_MINUTE_PATTERN),
                        middleTitle = stringResource(id = R.string.common_t_max_min),
                        middleText = "↑${dailyWeather.temp.max.getValueWithUnit()}, ↓${dailyWeather.temp.min.getValueWithUnit()}",
                        bottomTitle = stringResource(id = R.string.common_pop_short),
                        bottomText = stringResource(
                            id = R.string.common_percent,
                            dailyWeather.pop.times(100).toInt()
                        )
                    )

                    DayLightUVIndexInfoColumn(
                        topTitle = stringResource(id = R.string.common_daylight),
                        topText = calculateDaylight(dailyWeather.getSunrise(), dailyWeather.getSunset()),
                        middleTitle = stringResource(id = R.string.common_uv_index),
                        middleText = state.uvi.value.toInt(),
                        bottomTitle = stringResource(id = R.string.common_humidity),
                        bottomText = state.humidity.getAsString(),
                        dailyWeather = dailyWeather,
                        currentWeather = state
                    )

                    InfoColumn(
                        topTitle = stringResource(id = R.string.common_sunset),
                        topText = state.getSunset().toDate(HOUR_MINUTE_PATTERN),
                        middleTitle = stringResource(id = R.string.common_pressure),
                        middleText = state.pressure.getValueWithUnit(),
                        bottomTitle = stringResource(id = R.string.common_wind_speed_title),
                        bottomText = stringResource(
                            R.string.common_wind_speed,
                            state.windSpeed.getValueWithUnit(),
                            convertWindDegreesToDirection(state.windDeg)
                        )
                    )
                }
            }

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

                    .rotate(rotationDegree),
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = LocalCustomColorsPalette.current.text
            )
        }
    }
}

@Composable
fun InfoColumn(
    topTitle: String,
    topText: String,
    middleTitle: String,
    middleText: String,
    bottomTitle: String,
    bottomText: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        InfoRow(
            title = topTitle,
            value = topText
        )

        Spacer(modifier = Modifier.height(15.dp))

        InfoRow(
            title = middleTitle,
            value = middleText
        )

        Spacer(modifier = Modifier.height(15.dp))

        InfoRow(
            title = bottomTitle,
            value = bottomText
        )
    }
}

@Composable
fun DayLightUVIndexInfoColumn(
    dailyWeather: DailyWeather,
    currentWeather: CurrentWeather,
    topTitle: String,
    topText: String,
    middleTitle: String,
    middleText: Int,
    bottomTitle: String,
    bottomText: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                InfoRow(
                    title = topTitle,
                    value = topText
                )
            }

            SunPathComposable(min = dailyWeather.sunrise.toFloat(), max = dailyWeather.sunset.toFloat(), current = currentWeather.timestamp.toFloat())
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = middleTitle,
            color = LocalCustomColorsPalette.current.textSecondary,
            fontSize = 9.sp,
            lineHeight = 9.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        UVIndexLine(indexOfText = middleText)

        Spacer(modifier = Modifier.height(15.dp))

        InfoRow(
            title = bottomTitle,
            value = bottomText
        )
    }
}

@Composable
fun InfoRow(
    title: String,
    value: String
) {
    Text(
        text = title,
        color = LocalCustomColorsPalette.current.textSecondary,
        fontSize = 9.sp,
        lineHeight = 9.sp
    )

    Spacer(modifier = Modifier.height(5.dp))

    Text(
        text = value,
        color = LocalCustomColorsPalette.current.text,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        fontWeight = FontWeight.Medium
    )
}


@PreviewLightDark
@Composable
fun CurrentWeatherCardPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            CurrentWeatherCard(
                state = MockDataHelper.createMockCurrent(),
                dailyWeather = MockDataHelper.createDailyWeather()
            )
        }
    }
}