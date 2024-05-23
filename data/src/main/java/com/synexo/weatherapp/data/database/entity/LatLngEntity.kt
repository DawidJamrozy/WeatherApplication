package com.synexo.weatherapp.data.database.entity

import androidx.room.ColumnInfo
import com.synexo.weatherapp.domain.model.CityShortData

internal class LatLngEntity(
    @ColumnInfo(name = "lat")
    val lat: Double,
    @ColumnInfo(name = "lon")
    val lng: Double,
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
) {

    fun toDomainModel() = CityShortData(
        lat = lat,
        lng = lng,
        language = language,
        updatedAt = updatedAt,
        id = id
    )
}