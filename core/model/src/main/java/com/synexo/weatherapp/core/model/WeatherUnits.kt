package com.synexo.weatherapp.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class WeatherUnits(
    val degrees: DegreesUnit = DegreesUnit.Celsius,
    val wind: WindUnit = WindUnit.MetersPerSecond,
    val pressure: PressureUnit = PressureUnit.Hectopascals,
) : Parcelable