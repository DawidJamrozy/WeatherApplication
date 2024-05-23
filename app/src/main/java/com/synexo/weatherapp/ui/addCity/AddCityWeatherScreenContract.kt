package com.synexo.weatherapp.ui.addCity

import android.os.Parcelable
import com.synexo.weatherapp.domain.model.CityWeather
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddCityScreenState(
    val cityWeather: CityWeather? = null
): Parcelable

sealed class AddCityScreenViewEvent {

    data class SetCityWeather(val cityWeather: CityWeather) : AddCityScreenViewEvent()
    data object OnCancelClick : AddCityScreenViewEvent()
    data object OnAddCityClick : AddCityScreenViewEvent()
    data class OnDailyItemClick(val index: Int) : AddCityScreenViewEvent()
    data object OnHourlyItemClick : AddCityScreenViewEvent()
}

sealed class AddCityScreenViewModelEvent {
    data object NavigateBack : AddCityScreenViewModelEvent()
    data object CityAddSuccess : AddCityScreenViewModelEvent()
    data class GoToFutureDailyForecastScreen(val index: Int) : AddCityScreenViewModelEvent()
    data object GoToFutureHourlyForecastScreen : AddCityScreenViewModelEvent()
}