package com.synexo.weatherapp.domain.model.weather

import android.os.Parcelable
import com.synexo.weatherapp.domain.model.Units
import com.synexo.weatherapp.domain.model.WindUnit
import kotlinx.parcelize.Parcelize

@Parcelize
class WindSpeed(
    val rawValue: Double,
    private val windUnit: WindUnit,
    private val units: Units
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
        return when (units) {
            Units.Metric -> rawValue.toInt()
            Units.Imperial -> rawValue.toInt()
        }
    }

    private fun getAsKilometerPerHour(): Int {
        return when (units) {
            Units.Metric -> rawValue.times(3.6).toInt()
            Units.Imperial -> rawValue.times(3.6).toInt()
        }
    }

}