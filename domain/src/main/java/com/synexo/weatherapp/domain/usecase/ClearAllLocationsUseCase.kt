package com.synexo.weatherapp.domain.usecase

import com.synexo.weatherapp.core.BaseSuspendUseCase
import com.synexo.weatherapp.domain.repository.CityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearAllLocationsUseCase @Inject constructor(
    private val cityRepository: CityRepository
) : BaseSuspendUseCase<Unit, Unit> {

    override suspend fun invoke(params: Unit) {
        cityRepository.deleteAllCityData()
    }

}