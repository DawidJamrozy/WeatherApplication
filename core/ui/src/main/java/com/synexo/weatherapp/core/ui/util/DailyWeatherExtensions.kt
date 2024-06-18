package com.synexo.weatherapp.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.synexo.weatherapp.core.model.weather.DailyWeather
import com.synexo.weatherapp.core.ui.HOUR_MINUTE_PATTERN
import com.synexo.weatherapp.core.ui.R
import com.synexo.weatherapp.core.ui.calculateDaylight
import com.synexo.weatherapp.core.ui.convertWindDegreesToDirection
import com.synexo.weatherapp.core.ui.toDate


fun DailyWeather.getDayPeriodSummaryItems(): List<DayPartTempData> {
    return listOf(
        DayPartTempData(
            timeOfDay = R.string.common_morning,
            temperature = temp.morn.getValueWithUnit(),
            feelsLikeTemperature = feelsLike.morn.getValueWithUnit()
        ),
        DayPartTempData(
            timeOfDay = R.string.common_afternoon,
            temperature = temp.day.getValueWithUnit(),
            feelsLikeTemperature = feelsLike.day.getValueWithUnit()
        ),
        DayPartTempData(
            timeOfDay = R.string.common_evening,
            temperature = temp.eve.getValueWithUnit(),
            feelsLikeTemperature = feelsLike.eve.getValueWithUnit()
        ),
        DayPartTempData(
            timeOfDay = R.string.common_night,
            temperature = temp.night.getValueWithUnit(),
            feelsLikeTemperature = feelsLike.night.getValueWithUnit()
        )
    )
}

@Composable
fun DailyWeather.toDayWeatherConditionsItems(): List<WeatherConditionsItem> {
    return mutableListOf(
        WeatherConditionsItem(
            R.drawable.ic_sunrise,
            R.string.common_sunrise,
            getSunrise().toDate(HOUR_MINUTE_PATTERN)
        ),
        WeatherConditionsItem(
            R.drawable.ic_sunset,
            R.string.common_sunset,
            getSunset().toDate(HOUR_MINUTE_PATTERN)
        ),
        WeatherConditionsItem(
            R.drawable.ic_sun,
            R.string.common_daylight,
            calculateDaylight(getSunrise(), getSunset())
        ),
        WeatherConditionsItem(
            R.drawable.moon,
            R.string.common_moonrise,
            getMoonrise().toDate(HOUR_MINUTE_PATTERN)
        ),
        WeatherConditionsItem(
            R.drawable.ic_pressure,
            R.string.common_pressure,
            pressure.getValueWithUnit()
        ),
        WeatherConditionsItem(
            R.drawable.ic_humidity,
            R.string.common_humidity,
            "${humidity}%"
        ),
        WeatherConditionsItem(
            R.drawable.ic_wind_speed,
            R.string.common_wind_speed_title,
            "${windSpeed.getValueWithUnit()}, ${convertWindDegreesToDirection(windDeg)}"
        ),
        WeatherConditionsItem(
            R.drawable.ic_clouds,
            R.string.common_cloudiness,
            "${clouds}%"
        ),
        WeatherConditionsItem(
            R.drawable.ic_percent,
            R.string.common_pop,
            "${pop.times(100).toInt()}%"
        ),
        WeatherConditionsItem(
            R.drawable.ic_uv_index,
            R.string.common_uv,
            "${uvi.value.toInt()} - ${stringResource(id = uvi.getDescriptionResId())}"
        ),
    ).apply {
        windGust?.let {
            WeatherConditionsItem(
                R.drawable.ic_wind_gust,
                R.string.common_wind_gust,
                it.getValueWithUnit()
            )
        }
        rain?.let {
            WeatherConditionsItem(
                R.drawable.ic_clouds_rain,
                R.string.common_rain,
                "$it mm"
            )
        }
        snow?.let {
            WeatherConditionsItem(
                R.drawable.ic_snow,
                R.string.common_snow,
                "$it mm"
            )
        }
    }.toList()
}