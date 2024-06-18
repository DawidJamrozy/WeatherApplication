package com.synexo.weatherapp.addCity.domain.usecase

import com.synexo.weatherapp.core.domain.usecase.base.BaseSuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveWeatherCityDataUseCase @Inject constructor(
    private val cityRepository: com.synexo.weatherapp.core.domain.repository.CityRepository
) : BaseSuspendUseCase<SaveWeatherCityDataUseCase.Params, Unit> {

    override suspend fun invoke(params: Params) {
        cityRepository.saveCityData(params.cityWeather)
    }

    data class Params(
        val cityWeather: com.synexo.weatherapp.core.model.CityWeather
    )

}