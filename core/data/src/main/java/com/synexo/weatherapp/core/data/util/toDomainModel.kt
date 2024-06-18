package com.synexo.weatherapp.core.data.util

import com.synexo.weatherapp.core.database.entity.LatLngEntity
import com.synexo.weatherapp.core.model.CityShortData

internal fun LatLngEntity.toDomainModel() = CityShortData(
    lat = lat,
    lng = lng,
    language = language,
    updatedAt = updatedAt,
    id = id
)