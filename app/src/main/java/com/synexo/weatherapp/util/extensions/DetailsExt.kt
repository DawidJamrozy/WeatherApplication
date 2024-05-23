package com.synexo.weatherapp.util.extensions

import com.synexo.weatherapp.R
import com.synexo.weatherapp.domain.model.weather.Details

fun Details.getIconResId(): Int {
    return when (this.icon) {
        "01d" -> R.drawable.ic_sun
        "01n" -> R.drawable.moon
        "02d" -> R.drawable.sun_clouds
        "02n" -> R.drawable.moon_clouds
        "03d", "03n", "04d", "04n" -> R.drawable.ic_clouds
        "09d", "09n" -> R.drawable.ic_clouds_rain
        "10d" -> R.drawable.sun_clouds_rain
        "10n" -> R.drawable.moon_clouds_rain
        "11d" -> R.drawable.sun_clouds_rain_lightning
        "11n" -> R.drawable.moon_clouds_rain_lightning
        "13d" -> R.drawable.sun_clouds_snow
        "13n" -> R.drawable.moon_clouds_snow
        "50d" -> R.drawable.sun_clouds_mist
        "50n" -> R.drawable.moon_clouds_mist
        else -> R.drawable.ic_sun
    }
}