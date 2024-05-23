package com.synexo.weatherapp.domain.model.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Details(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
): Parcelable