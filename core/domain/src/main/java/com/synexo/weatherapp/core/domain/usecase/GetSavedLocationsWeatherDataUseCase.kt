package com.synexo.weatherapp.core.domain.usecase

import com.synexo.weatherapp.core.domain.repository.CityRepository
import com.synexo.weatherapp.core.domain.repository.SettingsRepository
import com.synexo.weatherapp.core.domain.usecase.base.BaseSuspendUseCase
import com.synexo.weatherapp.core.model.AppError
import com.synexo.weatherapp.core.model.CityWeather
import com.synexo.weatherapp.core.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSavedLocationsWeatherDataUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val settingsRepository: SettingsRepository
) : BaseSuspendUseCase<Unit, Flow<Resource<GetSavedLocationsWeatherDataUseCase.Result>>> {
    override suspend fun invoke(params: Unit): Flow<Resource<Result>> {
        return flow {
            emit(Resource.Loading)

            val weatherUnits = settingsRepository
                .getSettings()
                .first()
                .mapToWeatherUnits()

            cityRepository
                .getAllCityData(weatherUnits)
                .collect { emit(Resource.Success(Result(it))) }
        }.catch { error ->
            when (error) {
                is AppError -> emit(Resource.Error(error))
                else -> emit(Resource.Error(AppError.UnknownError(error.message)))
            }
        }
    }

    data class Result(
        val weatherData: List<CityWeather>
    )
}