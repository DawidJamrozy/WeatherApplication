package com.synexo.weatherapp.domain.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Snow(
    val h: Double
): Parcelable