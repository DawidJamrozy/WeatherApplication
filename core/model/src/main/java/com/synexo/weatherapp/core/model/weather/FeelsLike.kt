package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class FeelsLike(
    val day: Temperature,
    val eve: Temperature,
    val morn: Temperature,
    val night: Temperature
) : Parcelable {

    fun getBiggestTemp(): Temperature {
        return listOf(day, eve, morn, night).maxBy { it.getValue() }
    }

    fun getLowestTemp(): Temperature {
        return listOf(day, eve, morn, night).minBy { it.getValue() }
    }

}