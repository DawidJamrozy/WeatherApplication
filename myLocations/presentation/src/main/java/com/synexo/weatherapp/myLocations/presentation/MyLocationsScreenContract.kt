package com.synexo.weatherapp.myLocations.presentation

import android.os.Parcelable
import com.synexo.weatherapp.core.model.AppError
import com.synexo.weatherapp.core.model.CityWeather
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyLocationsScreenState(
    val savedCities: List<CityWeather> = emptyList(),
    val deleteCityDialogData: DeleteCityDialogData? = null,
    val cityUpdatedDialogData: CityUpdatedDialogData? = null,
    val isInEditMode: Boolean = false,
    val appError: AppError? = null,
    val isLoading: Boolean = false,
    val originalOrder: Map<String, Int>? = null,
) : Parcelable

@Parcelize
class DeleteCityDialogData(
    val index: Int,
    val cityName: String
) : Parcelable

@Parcelize
class CityUpdatedDialogData(
    val cityName: String
) : Parcelable

sealed class MyLocationsScreenViewEvent {
    data object GoToSearchScreen : MyLocationsScreenViewEvent()
    data class GoToWeatherScreen(val cityIndex: Int) : MyLocationsScreenViewEvent()
    data object DismissErrorDialog : MyLocationsScreenViewEvent()
    data class ShowDeleteLocationDialog(val cityName: String, val index: Int) :
        MyLocationsScreenViewEvent()
    data object HideDeleteLocationDialog : MyLocationsScreenViewEvent()
    data object HideCityUpdatedDialog : MyLocationsScreenViewEvent()

    data class CityUpdated(val cityName: String): MyLocationsScreenViewEvent()
    data class DeleteLocation(val index: Int) : MyLocationsScreenViewEvent()
    data class SwapCities(val from: Int, val to: Int) : MyLocationsScreenViewEvent()
    data object SwitchEditMode : MyLocationsScreenViewEvent()
}

sealed class MyLocationsScreenViewModelEvent {

    data class NavigateToAddCityWeather(
        val cityWeather: CityWeather
    ) : MyLocationsScreenViewModelEvent()

    data object NavigateToSearchScreen: MyLocationsScreenViewModelEvent()

    data class NavigateToWeatherScreen(
        val cityIndex: Int
    ) : MyLocationsScreenViewModelEvent()

}