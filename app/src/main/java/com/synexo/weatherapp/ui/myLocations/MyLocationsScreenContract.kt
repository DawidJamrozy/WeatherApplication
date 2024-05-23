package com.synexo.weatherapp.ui.myLocations

import android.os.Parcelable
import com.synexo.weatherapp.core.AppError
import com.synexo.weatherapp.data.model.SearchCityDetails
import com.synexo.weatherapp.domain.model.CityWeather
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyLocationsScreenState(
    val searchInput: String = "",
    val locations: List<SearchCityDetails> = emptyList(),
    val savedCities: List<CityWeather> = emptyList(),
    val deleteCityDialogData: DeleteCityDialogData? = null,
    val cityUpdatedDialogData: CityUpdatedDialogData? = null,
    val askForLocationPermission: Boolean = false,
    val wasLocationPermissionDenied: Boolean = false,
    val showLocationPermissionRationaleDialog: Boolean = false,
    val isSearchInputFocused: Boolean = false,
    val isInEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val appError: AppError? = null,
    val originalOrder: Map<String, Int>? = null,
    val isLocateMeInProgress: Boolean = false
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
    data class OnCityClicked(val city: SearchCityDetails) : MyLocationsScreenViewEvent()
    data class GoToWeatherScreen(val cityIndex: Int) : MyLocationsScreenViewEvent()
    data class SearchInputChanged(val input: String) : MyLocationsScreenViewEvent()
    data object ClearSearchInput : MyLocationsScreenViewEvent()
    data object CancelLocationSearch : MyLocationsScreenViewEvent()
    data object OnLocationPermissionGranted : MyLocationsScreenViewEvent()
    data object AskForLocationPermission : MyLocationsScreenViewEvent()
    data object DismissErrorDialog : MyLocationsScreenViewEvent()
    data class SetLocationPermissionRationaleDialogState(val isVisible: Boolean) :
        MyLocationsScreenViewEvent()
    data class ShowDeleteLocationDialog(val cityName: String, val index: Int) :
        MyLocationsScreenViewEvent()
    data object HideDeleteLocationDialog : MyLocationsScreenViewEvent()
    data object HideCityUpdatedDialog : MyLocationsScreenViewEvent()
    data class DeleteLocation(val index: Int) : MyLocationsScreenViewEvent()
    data class SwapCities(val from: Int, val to: Int) : MyLocationsScreenViewEvent()
    data class SetSearchInputFocus(val isFocused: Boolean) : MyLocationsScreenViewEvent()
    data object SwitchEditMode : MyLocationsScreenViewEvent()
}

sealed class MyLocationsScreenViewModelEvent {

    data class NavigateToAddCityWeather(
        val cityWeather: CityWeather
    ) : MyLocationsScreenViewModelEvent()

    data class NavigateToWeatherScreen(
        val cityIndex: Int
    ) : MyLocationsScreenViewModelEvent()

}