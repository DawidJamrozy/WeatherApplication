package com.synexo.weatherapp.data.model

import androidx.annotation.DrawableRes

class WeatherConditionsItem(
    @DrawableRes val icon: Int,
    val title: Int,
    val text: String
)