package com.synexo.weatherapp.util.extensions

import com.synexo.weatherapp.R
import com.synexo.weatherapp.domain.model.weather.UVIndex

fun UVIndex.getDescriptionResId(): Int {
    return when (this.value) {
        in 0.0..2.9 -> R.string.common_uv_low
        in 3.0..5.9 -> R.string.common_uv_moderate
        in 6.0..7.9 -> R.string.common_uv_high
        in 8.0..10.9 -> R.string.common_uv_very_high
        in 11.0..40.0 -> R.string.common_uv_extreme
        else -> R.string.common_uv_unknown
    }
}