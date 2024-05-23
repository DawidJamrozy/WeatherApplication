package com.synexo.weatherapp.ui.weather

import android.os.Parcelable
import com.synexo.weatherapp.core.AppError
import com.synexo.weatherapp.domain.model.CityWeather
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherScreenState(
    val locations: List<CityWeather> = listOf(),
    val isLoading: Boolean = false,
    val initialLocationIndex: Int = 0,
    val isRefreshing: Boolean = false,
    val appError: AppError? = null
) : Parcelable

sealed class WeatherScreenViewEvent {
    data class OnDailyItemClick(
        val locationIndex: Int,
        val dailyIndex: Int
    ) : WeatherScreenViewEvent()

    data class OnHourlyItemClick(val locationIndex: Int) : WeatherScreenViewEvent()

    data object OnNoDataButtonClick : WeatherScreenViewEvent()

    data object DismissErrorDialog : WeatherScreenViewEvent()

    data class RefreshWeatherData(val position: Int) : WeatherScreenViewEvent()

}

sealed class WeatherScreenViewModelEvent {
    data class GoToFutureDailyForecastScreen(
        val cityWeather: CityWeather,
        val index: Int
    ) : WeatherScreenViewModelEvent()

    data class GoToFutureHourlyForecastScreen(
        val cityWeather: CityWeather
    ) : WeatherScreenViewModelEvent()

    data object GoToMyLocationsScreen : WeatherScreenViewModelEvent()

}