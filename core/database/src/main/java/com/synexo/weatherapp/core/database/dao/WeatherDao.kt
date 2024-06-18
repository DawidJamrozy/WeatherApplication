package com.synexo.weatherapp.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.synexo.weatherapp.core.database.entity.CityEntity
import com.synexo.weatherapp.core.database.entity.LatLngEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {
    @Insert
    suspend fun saveWeatherEntity(cityEntity: CityEntity)

    @Update
    suspend fun updateWeatherEntity(cityEntity: CityEntity)

    @Query("SELECT * FROM city_table ORDER BY `order` ASC")
    fun getSavedCityWeatherEntities(): Flow<List<CityEntity>>

    @Query("DELETE FROM city_table")
    suspend fun clearAllLocations()

    @Query("SELECT max(`order`) FROM city_table")
    suspend fun getMaxOrder(): Int?

    @Query("DELETE FROM city_table WHERE id = :id")
    suspend fun deleteLocation(id: String)

    @Query("UPDATE city_table SET weather = :weather, language =  COALESCE(:language, language), city_name = COALESCE(:cityName, city_name), country_name = COALESCE(:countryName, country_name), admin_area = COALESCE(:adminArea, admin_area) WHERE id = :id")
    suspend fun updateWeatherData(
        id: String,
        weather: String,
        language: String?,
        cityName: String?,
        countryName: String?,
        adminArea: String?
    )

    @Query("UPDATE city_table SET `order` = :newOrder WHERE id = :id")
    suspend fun updateOrder(id: String, newOrder: Int)

    @Query("SELECT id, lat, lon, language, updated_at FROM city_table")
    suspend fun getAllLatLng(): List<LatLngEntity>

    @Query("SELECT `order` FROM city_table WHERE id = :id")
    suspend fun getOrder(id: String): Int?

    @Transaction
    suspend fun updateOrders(orderMap: Map<String, Int>) {
        for ((id, newOrder) in orderMap) {
            updateOrder(id, newOrder)
        }
    }

}