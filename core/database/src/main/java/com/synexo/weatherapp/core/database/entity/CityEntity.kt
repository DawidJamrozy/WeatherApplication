package com.synexo.weatherapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("city_table")
data class CityEntity(

    @ColumnInfo(name = "weather")
    val weather: String,

    @ColumnInfo(name = "city_name")
    val cityName: String,

    @ColumnInfo(name = "lat")
    val lat: Double,

    @ColumnInfo(name = "lon")
    val lon: Double,

    @ColumnInfo(name = "country_name")
    val countryName: String,

    @ColumnInfo(name= "language")
    val language: String,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,

    @ColumnInfo(name = "admin_area")
    val adminArea: String?,

    @ColumnInfo(name = "order")
    val order: Int,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String
)