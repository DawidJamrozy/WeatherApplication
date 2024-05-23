package com.synexo.weatherapp.domain.usecase

import androidx.datastore.preferences.core.stringPreferencesKey
import com.synexo.weatherapp.core.BaseSuspendUseCase
import com.synexo.weatherapp.domain.model.AppSettings
import com.synexo.weatherapp.domain.repository.DataStoreRepository
import com.synexo.weatherapp.domain.util.DataStoreKeys.WEATHER_SETTINGS_KEY
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveAppSettingsUseCase @Inject constructor(
    private val json: Json,
    private val dataStoreRepository: DataStoreRepository
) : BaseSuspendUseCase<SaveAppSettingsUseCase.Params, Unit>{

    override suspend fun invoke(params: Params) {
        val jsonData = json.encodeToString(AppSettings.serializer(), params.appSettings)
        dataStoreRepository.saveToDataStore(stringPreferencesKey(WEATHER_SETTINGS_KEY), jsonData)
    }

    data class Params(
        val appSettings: AppSettings
    )

}