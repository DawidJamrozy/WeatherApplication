package com.synexo.weatherapp.data.model

import androidx.annotation.StringRes

class DayPartTempData(
    @StringRes val timeOfDay: Int,
    val temperature: String,
    val feelsLikeTemperature: String
)