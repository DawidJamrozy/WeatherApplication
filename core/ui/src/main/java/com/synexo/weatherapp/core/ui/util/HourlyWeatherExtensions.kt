package com.synexo.weatherapp.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.synexo.weatherapp.core.model.weather.HourlyWeather
import com.synexo.weatherapp.core.ui.R
import com.synexo.weatherapp.core.ui.convertWindDegreesToDirection

@Composable
fun HourlyWeather.toDayWeatherConditionsItems(): List<WeatherConditionsItem> {
    return mutableListOf(
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
            R.string.common_pop_short,
            "${pop.times(100).toInt()}%"
        ),
        WeatherConditionsItem(
            R.drawable.ic_uv_index,
            R.string.common_uv,
            "${uvi.value.toInt()} - ${stringResource(id = uvi.getDescriptionResId())}"
        ),
    ).apply {
        windGust
            ?.let {
                WeatherConditionsItem(
                    R.drawable.ic_wind_gust,
                    R.string.common_wind_gust,
                    it.getValueWithUnit()
                )
            }
            ?.let { add(it) }
        rain
            ?.let {
                WeatherConditionsItem(
                    R.drawable.ic_clouds_rain,
                    R.string.common_rain,
                    "${it.h} mm"
                )
            }
            ?.let { add(it) }
        snow
            ?.let {
            WeatherConditionsItem(
                R.drawable.ic_snow,
                R.string.common_snow,
                "${it.h }mm"
            )
        }?.let { add(it) }
    }.toList()
}