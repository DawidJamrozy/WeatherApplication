package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import com.synexo.weatherapp.core.model.DegreesUnit
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
class Temperature(
    val rawValue: Double,
    private val degreesUnit: DegreesUnit
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
        return rawValue.toInt()
    }


    private fun getAsFahrenheit(): Int {
        return (rawValue * 9 / 5 + 32).toInt()
    }

}