package com.synexo.weatherapp.domain.usecase

import com.synexo.weatherapp.core.BaseUseCase
import com.synexo.weatherapp.domain.model.AppSettings
import com.synexo.weatherapp.domain.service.SettingsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAppSettingsUseCase @Inject constructor(
    private val settingsService: SettingsService
) :BaseUseCase<Unit, Flow<GetAppSettingsUseCase.Result>> {

    override fun invoke(params: Unit): Flow<Result> {
        return settingsService
            .getSettings()
            .map { Result(it) }
    }

    data class Result(
        val appSettings: AppSettings
    )

}