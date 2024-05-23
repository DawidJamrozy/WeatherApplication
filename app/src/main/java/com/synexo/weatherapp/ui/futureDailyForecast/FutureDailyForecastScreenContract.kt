package com.synexo.weatherapp.ui.futureDailyForecast

import android.os.Parcelable
import com.synexo.weatherapp.domain.model.CityWeather
import kotlinx.parcelize.Parcelize

@Parcelize
data class FutureDailyForecastScreenState(
    val isLoading: Boolean = false,
    val abbreviatedDialogVisible: Boolean = false,
    val cityWeather: CityWeather? = null,
    val initialDayIndex: Int = 0,
): Parcelable

sealed class FutureDailyForecastScreenViewEvent {

    data class SetCityWeather(val cityWeather: CityWeather) : FutureDailyForecastScreenViewEvent()
    data object OnBackClick : FutureDailyForecastScreenViewEvent()
    data class SetAbbreviatedDialogState(val visible: Boolean) : FutureDailyForecastScreenViewEvent()
}

sealed class FutureDailyForecastScreenViewModelEvent {
    data object NavigateBack : FutureDailyForecastScreenViewModelEvent()

}