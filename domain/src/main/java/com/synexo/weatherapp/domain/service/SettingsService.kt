package com.synexo.weatherapp.domain.service

import androidx.datastore.preferences.core.stringPreferencesKey
import com.synexo.weatherapp.domain.model.AppSettings
import com.synexo.weatherapp.domain.model.DegreesUnit
import com.synexo.weatherapp.domain.model.PressureUnit
import com.synexo.weatherapp.domain.model.Theme
import com.synexo.weatherapp.domain.model.WindUnit
import com.synexo.weatherapp.domain.repository.DataStoreRepository
import com.synexo.weatherapp.domain.util.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsService @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val json: Json
) {

    fun getSettings(): Flow<AppSettings> {
        return dataStoreRepository
            .readFromDataStore(stringPreferencesKey(DataStoreKeys.WEATHER_SETTINGS_KEY))
            .map { data ->
                data
                    ?.let { json.decodeFromString(AppSettings.serializer(), it) }
                    ?: getDefaultWeatherSettings()
            }
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