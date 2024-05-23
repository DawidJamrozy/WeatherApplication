package com.synexo.weatherapp.domain.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Temp(
    val day: Temperature,
    val eve: Temperature,
    val max: Temperature,
    val min: Temperature,
    val morn: Temperature,
    val night: Temperature
): Parcelable