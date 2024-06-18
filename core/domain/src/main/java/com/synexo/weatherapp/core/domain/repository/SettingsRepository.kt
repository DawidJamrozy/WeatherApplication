package com.synexo.weatherapp.core.domain.repository

import com.synexo.weatherapp.core.model.AppSettings
import kotlinx.coroutines.flow.Flow

/**
 * Interface for managing application data storage.
 */
interface SettingsRepository {

    fun getSettings(): Flow<AppSettings>

    suspend fun saveSettings(appSettings: AppSettings)

    suspend fun deleteAllCityData()

}