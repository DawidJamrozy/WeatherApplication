package com.synexo.weatherapp.ui.futureHourlyForecast

import android.os.Parcelable
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.synexo.weatherapp.domain.model.WeatherUnits
import com.synexo.weatherapp.domain.model.weather.HourlyWeather
import com.synexo.weatherapp.domain.model.weather.Weather
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class FutureHourlyForecastScreenState(
    val loading: Boolean = false,
    val hourlyForecastViewType: HourlyForecastViewType = HourlyForecastViewType.GRAPH,
    val minTemp: Float = 0f,
    val maxTemp: Float = 0f,
    val minPressure: Float = 0f,
    val maxPressure: Float = 0f,
    val minHumidity: Float = 0f,
    val maxHumidity: Float = 100f,
    val minRain: Float = 0f,
    val maxRain: Float = 0f,
    val weatherUnits: WeatherUnits = WeatherUnits(),
    val hourlyData: List<HourlyWeather> = emptyList(),
    @IgnoredOnParcel
    val tempModelProducer: CartesianChartModelProducer = CartesianChartModelProducer.build(),
    @IgnoredOnParcel
    val pressureModelProducer: CartesianChartModelProducer = CartesianChartModelProducer.build(),
    @IgnoredOnParcel
    val humidityModelProducer: CartesianChartModelProducer = CartesianChartModelProducer.build(),
    @IgnoredOnParcel
    val rainModelProducer: CartesianChartModelProducer = CartesianChartModelProducer.build(),
    @IgnoredOnParcel
    val labelListKey: ExtraStore.Key<List<String>> = ExtraStore.Key(),
): Parcelable

sealed class FutureHourlyForecastScreenViewEvent {
    data object OnBackClick : FutureHourlyForecastScreenViewEvent()
    data class SetHourlyWeatherData(val weather: Weather) : FutureHourlyForecastScreenViewEvent()
    data object SwitchViewType : FutureHourlyForecastScreenViewEvent()
}

sealed class FutureHourlyForecastScreenViewModelEvent {
    data object NavigateBack : FutureHourlyForecastScreenViewModelEvent()
}

enum class HourlyForecastViewType {
    GRAPH,
    LIST
}