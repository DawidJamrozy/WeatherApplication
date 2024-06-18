package com.synexo.weatherapp.futureHourlyForecast.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.synexo.weatherapp.core.model.weather.HourlyWeather
import com.synexo.weatherapp.core.ui.HOUR_MINUTE_PATTERN
import com.synexo.weatherapp.core.ui.toDate
import com.synexo.weatherapp.core.ui.viewModel.BaseStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class FutureHourlyForecastScreenViewModel @Inject constructor(
    state: SavedStateHandle
) : BaseStateViewModel<FutureHourlyForecastScreenState, FutureHourlyForecastScreenViewModelEvent, FutureHourlyForecastScreenViewEvent>(
    state
) {

    private companion object {
        const val DELAY = 50L
    }

    override fun defaultState(): FutureHourlyForecastScreenState = FutureHourlyForecastScreenState()

    override fun onViewEvent(event: FutureHourlyForecastScreenViewEvent) {
        when (event) {
            is FutureHourlyForecastScreenViewEvent.OnBackClick -> {
                setViewModelEvent(FutureHourlyForecastScreenViewModelEvent.NavigateBack)
            }

            is FutureHourlyForecastScreenViewEvent.SetHourlyWeatherData -> {
                val hourlyWeather = event.weather.hourlyWeather
                modify {
                    copy(
                        hourlyData = hourlyWeather,
                        weatherUnits = event.weather.weatherUnits
                    )
                }
                viewModelScope.launch {
                    val xLabels = hourlyWeather
                        .map { data ->
                            data.getTimestamp().toDate(pattern = HOUR_MINUTE_PATTERN)
                        }
                    calculateTempData(hourlyWeather, xLabels)
                    calculatePressureData(hourlyWeather, xLabels)
                    calculateHumidityData(hourlyWeather, xLabels)
                    calculateRainData(hourlyWeather, xLabels)
                }
            }

            is FutureHourlyForecastScreenViewEvent.SwitchViewType -> {
                modify {
                    copy(
                        hourlyForecastViewType = if (viewState.value.hourlyForecastViewType == HourlyForecastViewType.GRAPH) {
                            HourlyForecastViewType.LIST
                        } else {
                            HourlyForecastViewType.GRAPH
                        }
                    )
                }
            }
        }
    }


    private fun calculateTempData(
        hourly: List<HourlyWeather>,
        xLabels: List<String>
    ) {
        viewModelScope.launch {
            val tempValues = hourly.map { it.temp.getValue() }
            val feelsLikeTempValues = hourly.map { it.feelsLike.getValue() }

            modify {
                copy(
                    minTemp = min(tempValues.min(), feelsLikeTempValues.min()).toFloat(),
                    maxTemp = max(tempValues.max(), feelsLikeTempValues.max()).toFloat()
                )
            }

            delay(DELAY)

            viewState
                .value
                .tempModelProducer
                .tryRunTransaction {
                    lineSeries {
                        series(tempValues)
                        series(feelsLikeTempValues)
                    }
                    updateExtras { it[viewState.value.labelListKey] = xLabels }
                }
        }
    }

    private fun calculatePressureData(
        hourly: List<HourlyWeather>,
        xLabels: List<String>
    ) {
        viewModelScope.launch {
            val pressure = hourly.map { it.pressure.getValue() }

            modify {
                copy(
                    minPressure = pressure.min().toFloat(),
                    maxPressure = pressure.max().toFloat(),
                )
            }

            delay(DELAY)

            viewState
                .value
                .pressureModelProducer
                .tryRunTransaction {
                    lineSeries { series(pressure) }
                    updateExtras { it[viewState.value.labelListKey] = xLabels }
                }
        }
    }

    private fun calculateHumidityData(
        hourly: List<HourlyWeather>,
        xLabels: List<String>
    ) {
        viewModelScope.launch {
            val humidity = hourly.map { it.humidity }

            delay(DELAY)

            viewState
                .value
                .humidityModelProducer
                .tryRunTransaction {
                    lineSeries { series(humidity) }
                    updateExtras { it[viewState.value.labelListKey] = xLabels }
                }
        }
    }

    private fun calculateRainData(
        hourly: List<HourlyWeather>,
        xLabels: List<String>
    ) {
        viewModelScope.launch {
            val rain = hourly.map { it.rain?.h?.toFloat() ?: 0f }

            modify {
                copy(
                    minRain = rain.min(),
                    maxRain = max(rain.max(), 1f),
                )
            }

            delay(DELAY)

            viewState
                .value
                .rainModelProducer
                .tryRunTransaction {
                    columnSeries { series(rain) }
                    updateExtras { it[viewState.value.labelListKey] = xLabels }
                }
        }
    }

}