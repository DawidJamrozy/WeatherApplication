package com.synexo.weatherapp.core.ui.util

import androidx.annotation.StringRes

class DayPartTempData(
    @StringRes val timeOfDay: Int,
    val temperature: String,
    val feelsLikeTemperature: String
)