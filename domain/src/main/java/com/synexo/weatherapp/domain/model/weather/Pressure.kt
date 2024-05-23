package com.synexo.weatherapp.domain.model.weather

import android.os.Parcelable
import com.synexo.weatherapp.domain.model.PressureUnit
import com.synexo.weatherapp.domain.model.Units
import kotlinx.parcelize.Parcelize

@Parcelize
class Pressure(
    val rawValue: Int,
    private val pressureUnit: PressureUnit,
    private val unit: Units
) : Parcelable {

    fun getValueWithUnit(): String {
        return when (pressureUnit) {
            PressureUnit.Hectopascals -> "${getValue()} hPa"
            PressureUnit.MillimetersOfMercury -> "${getValue()} mmHg"
        }
    }

    fun getValue(): Int {
        return when (pressureUnit) {
            PressureUnit.Hectopascals -> getAsHectoPascal()
            PressureUnit.MillimetersOfMercury -> getAsMillimeterOfMercury()
        }
    }


    // // 01.04.2024 in both cases pressure is returned in hPa
    private fun getAsHectoPascal(): Int {
        return when (unit) {
            Units.Imperial -> rawValue
            Units.Metric -> rawValue
        }
    }


    private fun getAsMillimeterOfMercury(): Int {
        return when (unit) {
            Units.Imperial -> rawValue.times(0.750062).toInt()
            Units.Metric -> rawValue.times(0.750062).toInt()
        }
    }
}