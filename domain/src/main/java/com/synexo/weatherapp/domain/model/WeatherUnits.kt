package com.synexo.weatherapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherUnits(
    val degrees: DegreesUnit = DegreesUnit.Celsius,
    val wind: WindUnit = WindUnit.MetersPerSecond,
    val pressure: PressureUnit = PressureUnit.Hectopascals,
): Parcelable