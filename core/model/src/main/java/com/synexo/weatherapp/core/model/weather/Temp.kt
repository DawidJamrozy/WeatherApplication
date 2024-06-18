package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Temp(
    val day: Temperature,
    val eve: Temperature,
    val max: Temperature,
    val min: Temperature,
    val morn: Temperature,
    val night: Temperature
): Parcelable