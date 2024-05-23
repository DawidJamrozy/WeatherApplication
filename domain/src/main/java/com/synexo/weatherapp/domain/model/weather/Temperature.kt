package com.synexo.weatherapp.domain.model.weather

import android.os.Parcelable
import com.synexo.weatherapp.domain.model.DegreesUnit
import com.synexo.weatherapp.domain.model.Units
import kotlinx.parcelize.Parcelize

@Parcelize
class Temperature(
    val rawValue: Double,
    private val degreesUnit: DegreesUnit,
    private val units: Units
): Parcelable {

    fun getValueWithUnit(): String {
        return when (degreesUnit) {
            DegreesUnit.Celsius -> "${getAsCelsius()}${getUnit()}"
            DegreesUnit.Fahrenheit -> "${getAsFahrenheit()}${getUnit()}"
        }
    }

    fun getValue():Int  {
        return when (degreesUnit) {
            DegreesUnit.Celsius -> getAsCelsius()
            DegreesUnit.Fahrenheit -> getAsFahrenheit()
        }
    }

    fun getUnit(): String {
        return when (degreesUnit) {
            DegreesUnit.Celsius -> "°C"
            DegreesUnit.Fahrenheit -> "°F"
        }
    }

    private fun getAsCelsius(): Int {
        return when (units) {
            Units.Metric -> rawValue.toInt()
            Units.Imperial -> ((rawValue - 32) * 5 / 9).toInt()
        }
    }


    private fun getAsFahrenheit(): Int {
        return when (units) {
            Units.Metric -> (rawValue * 9 / 5 + 32).toInt()
            Units.Imperial -> rawValue.toInt()
        }
    }

}