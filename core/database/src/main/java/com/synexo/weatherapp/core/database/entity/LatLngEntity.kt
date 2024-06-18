package com.synexo.weatherapp.core.database.entity

import androidx.room.ColumnInfo

class LatLngEntity(
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
)
