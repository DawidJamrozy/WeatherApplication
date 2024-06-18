package com.synexo.weatherapp.search.domain.usecase

import com.synexo.weatherapp.core.domain.usecase.base.BaseSuspendUseCase
import com.synexo.weatherapp.search.domain.model.CityNameSearchData
import com.synexo.weatherapp.search.domain.repository.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchCityUseCase @Inject constructor(
    private val searchRepository: SearchRepository
): BaseSuspendUseCase<SearchCityUseCase.Params, SearchCityUseCase.Result> {

    override suspend fun invoke(params: Params): Result {
        return searchRepository
            .searchCity(params.cityName)
            .let { Result(it) }
    }

    class Params(val cityName: String)

    class Result(val cities: List<CityNameSearchData>)

}