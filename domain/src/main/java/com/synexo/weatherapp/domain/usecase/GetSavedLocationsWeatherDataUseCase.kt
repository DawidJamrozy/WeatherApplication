package com.synexo.weatherapp.domain.usecase

import com.synexo.weatherapp.core.AppError
import com.synexo.weatherapp.core.BaseSuspendUseCase
import com.synexo.weatherapp.core.Resource
import com.synexo.weatherapp.domain.model.CityWeather
import com.synexo.weatherapp.domain.repository.CityRepository
import com.synexo.weatherapp.domain.service.SettingsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSavedLocationsWeatherDataUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val settingsService: SettingsService
) : BaseSuspendUseCase<Unit, Flow<Resource<GetSavedLocationsWeatherDataUseCase.Result>>> {
    override suspend fun invoke(params: Unit): Flow<Resource<Result>> {
        return flow {
            emit(Resource.Loading)

            val weatherUnits = settingsService.getSettings()
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