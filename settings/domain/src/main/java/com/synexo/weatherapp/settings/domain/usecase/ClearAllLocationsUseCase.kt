package com.synexo.weatherapp.settings.domain.usecase

import com.synexo.weatherapp.core.domain.repository.SettingsRepository
import com.synexo.weatherapp.core.domain.usecase.base.BaseSuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearAllLocationsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseSuspendUseCase<Unit, Unit> {

    override suspend fun invoke(params: Unit) {
        settingsRepository.deleteAllCityData()
    }

}