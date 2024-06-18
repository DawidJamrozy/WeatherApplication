package com.synexo.weatherapp.core.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Time(
    val timestamp: Long,
    val timezone: String
): Parcelable