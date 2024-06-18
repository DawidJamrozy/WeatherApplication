package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import com.synexo.weatherapp.core.model.WindUnit
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
class WindSpeed(
    val rawValue: Double,
    private val windUnit: WindUnit,
) : Parcelable {

    fun getValueWithUnit(): String {
        return when (windUnit) {
            WindUnit.MetersPerSecond -> "${getValue()} m/s"
            WindUnit.KilometersPerHour -> "${getValue()} km/h"
        }
    }

    fun getValue(): String {
        return when (windUnit) {
            WindUnit.MetersPerSecond -> "${getAsMeterPerSecond()}"
            WindUnit.KilometersPerHour -> "${getAsKilometerPerHour()}"
        }
    }

    // 01.04.2024 In both cases, the speed is returned in m/s
    private fun getAsMeterPerSecond(): Int {
        return rawValue.toInt()
    }

    private fun getAsKilometerPerHour(): Int {
        return rawValue.times(3.6).toInt()
    }

}