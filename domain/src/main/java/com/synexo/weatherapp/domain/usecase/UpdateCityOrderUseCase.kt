package com.synexo.weatherapp.domain.usecase

import com.synexo.weatherapp.core.BaseSuspendUseCase
import com.synexo.weatherapp.domain.repository.CityRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateCityOrderUseCase @Inject constructor(
    private val cityRepository: CityRepository
): BaseSuspendUseCase<UpdateCityOrderUseCase.Params, Unit> {

    override suspend fun invoke(params: Params) {
        cityRepository.updateCityDataOrder(params.idToPositionMap)
    }

    data class Params(
        val idToPositionMap: Map<String, Int>
    )

}