package com.synexo.weatherapp.weather.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.synexo.weatherapp.core.domain.usecase.GetSavedLocationsWeatherDataUseCase
import com.synexo.weatherapp.core.model.Resource
import com.synexo.weatherapp.core.ui.util.NavRoutes
import com.synexo.weatherapp.core.ui.viewModel.BaseStateViewModel
import com.synexo.weatherapp.weather.domain.usecase.RefreshWeatherDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getSavedLocationsWeatherDataUseCase: GetSavedLocationsWeatherDataUseCase,
    private val refreshWeatherDataUseCase: RefreshWeatherDataUseCase,
    state: SavedStateHandle,
) : BaseStateViewModel<WeatherScreenState, WeatherScreenViewModelEvent, WeatherScreenViewEvent>(
    state
) {

    override fun defaultState(): WeatherScreenState = WeatherScreenState()

    init {
        viewModelScope.launch {
            val dailyIndex = state.get<Int>(NavRoutes.CITY_INDEX_KEY)
            getSavedLocationsWeatherDataUseCase.invoke(Unit)
                .collect {
                    when (it) {
                        is Resource.Loading -> modify { copy(isLoading = true) }
                        is Resource.Success ->
                            modify {
                                copy(
                                    locations = it.data.weatherData,
                                    initialLocationIndex = dailyIndex ?: 0,
                                    isLoading = false
                                )
                            }

                        is Resource.Error ->
                            modify { copy(isLoading = false) }
                    }
                }
        }
    }

    override fun onViewEvent(event: WeatherScreenViewEvent) {
        when (event) {
            is WeatherScreenViewEvent.OnDailyItemClick -> {
                val weatherModel = viewState.value.locations[event.locationIndex]
                setViewModelEvent(
                    WeatherScreenViewModelEvent.GoToFutureDailyForecastScreen(
                        cityWeather = weatherModel,
                        index = event.dailyIndex
                    )
                )
            }

            is WeatherScreenViewEvent.OnHourlyItemClick -> {
                val weatherModel = viewState.value.locations[event.locationIndex]
                setViewModelEvent(
                    WeatherScreenViewModelEvent.GoToFutureHourlyForecastScreen(
                        cityWeather = weatherModel
                    )
                )
            }

            is WeatherScreenViewEvent.DismissErrorDialog -> {
                modify { copy(appError = null) }
            }

            WeatherScreenViewEvent.OnNoDataButtonClick -> {
                setViewModelEvent(WeatherScreenViewModelEvent.GoToMyLocationsScreen)
            }

            is WeatherScreenViewEvent.RefreshWeatherData -> {
                refreshCityWeatherData(event.position)
            }
        }
    }

    private fun refreshCityWeatherData(position: Int) {
        viewModelScope.launch {
            val cityWeather = viewState.value.locations[position]
            refreshWeatherDataUseCase
                .invoke(RefreshWeatherDataUseCase.Param(cityWeather))
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            update { copy(isRefreshing = true) }
                        }

                        is Resource.Success -> {
                            modify {
                                copy(
                                    locations = locations
                                        .toMutableList()
                                        .apply { set(position, it.data.cityWeather) }
                                        .toList(),
                                    isRefreshing = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            // Instant update causes issues with isRefreshing state
                            delay(100)
                            update {
                                copy(
                                    isRefreshing = false,
                                    appError = it.error
                                )
                            }
                        }
                    }
                }
        }
    }

}