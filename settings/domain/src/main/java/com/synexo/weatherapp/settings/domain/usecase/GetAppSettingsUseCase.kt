package com.synexo.weatherapp.settings.domain.usecase

import com.synexo.weatherapp.core.domain.repository.SettingsRepository
import com.synexo.weatherapp.core.domain.usecase.base.BaseUseCase
import com.synexo.weatherapp.core.model.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAppSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseUseCase<Unit, Flow<GetAppSettingsUseCase.Result>> {

    override fun invoke(params: Unit): Flow<Result> {
        return settingsRepository
            .getSettings()
            .map { Result(it) }
    }

    data class Result(
        val appSettings: AppSettings
    )

}