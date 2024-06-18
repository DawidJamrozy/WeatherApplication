package com.synexo.weatherapp.settings.domain.usecase

import com.synexo.weatherapp.core.domain.repository.SettingsRepository
import com.synexo.weatherapp.core.domain.usecase.base.BaseSuspendUseCase
import com.synexo.weatherapp.core.model.AppSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveAppSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseSuspendUseCase<SaveAppSettingsUseCase.Params, Unit> {

    override suspend fun invoke(params: Params) {
        settingsRepository.saveSettings(params.appSettings)
    }

    data class Params(
        val appSettings: AppSettings
    )

}