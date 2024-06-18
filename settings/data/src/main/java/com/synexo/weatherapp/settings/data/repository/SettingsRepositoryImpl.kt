package com.synexo.weatherapp.settings.data.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import com.synexo.weatherapp.core.database.dao.WeatherDao
import com.synexo.weatherapp.core.datastore.DataStoreProvider
import com.synexo.weatherapp.core.domain.repository.SettingsRepository
import com.synexo.weatherapp.core.model.AppSettings
import com.synexo.weatherapp.core.model.DegreesUnit
import com.synexo.weatherapp.core.model.PressureUnit
import com.synexo.weatherapp.core.model.Theme
import com.synexo.weatherapp.core.model.WindUnit
import com.synexo.weatherapp.settings.data.util.DataStoreKeys.WEATHER_SETTINGS_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * DataStoreRepository interface implementation.
 * Manages application data storage using DataStore.
 *
 * @constructor Instantiates DataStoreRepositoryImpl.
 * @property dataStoreProvider DataStoreProvider instance.
 * @property json Json instance.
 */
internal class SettingsRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val dataStoreProvider: DataStoreProvider,
    private val json: Json
) : SettingsRepository {

    override fun getSettings(): Flow<AppSettings> {
        return dataStoreProvider
            .readFromDataStore(stringPreferencesKey(WEATHER_SETTINGS_KEY), null)
            .map { data ->
                data
                    ?.let { json.decodeFromString(AppSettings.serializer(), it) }
                    ?: getDefaultWeatherSettings()
            }
    }

    override suspend fun saveSettings(appSettings: AppSettings) {
        val jsonData = json.encodeToString(AppSettings.serializer(), appSettings)
        dataStoreProvider.saveToDataStore(stringPreferencesKey(WEATHER_SETTINGS_KEY), jsonData)
    }

    override suspend fun deleteAllCityData() {
        weatherDao.clearAllLocations()
    }

    private fun getDefaultWeatherSettings(): AppSettings {
        return AppSettings(
            degrees = DegreesUnit.Celsius,
            wind = WindUnit.MetersPerSecond,
            pressure = PressureUnit.Hectopascals,
            theme = Theme.System
        )
    }
}