package com.synexo.weatherapp.search.presentation

import android.os.Parcelable
import com.synexo.weatherapp.core.model.AppError
import com.synexo.weatherapp.core.model.CityWeather
import com.synexo.weatherapp.search.presentation.model.SearchCityDetails
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchScreenState(
    val searchInput: String = "",
    val locations: List<SearchCityDetails> = emptyList(),
    val savedCities: List<CityWeather> = emptyList(),
    val askForLocationPermission: Boolean = false,
    val wasLocationPermissionDenied: Boolean = false,
    val showLocationPermissionRationaleDialog: Boolean = false,
    val isLoading: Boolean = false,
    val appError: AppError? = null,
    val isLocateMeInProgress: Boolean = false
) : Parcelable

sealed class SearchScreenViewEvent {
    data class OnCityClicked(val city: SearchCityDetails) : SearchScreenViewEvent()
    data class GoToWeatherScreen(val cityIndex: Int) : SearchScreenViewEvent()
    data class SearchInputChanged(val input: String) : SearchScreenViewEvent()
    data object ClearSearchInput : SearchScreenViewEvent()
    data object CancelLocationSearch : SearchScreenViewEvent()
    data object OnLocationPermissionGranted : SearchScreenViewEvent()
    data object AskForLocationPermission : SearchScreenViewEvent()
    data object DismissErrorDialog : SearchScreenViewEvent()
    data class SetLocationPermissionRationaleDialogState(val isVisible: Boolean) :
        SearchScreenViewEvent()
}

sealed class SearchScreenViewModelEvent {

    data class NavigateToAddCityWeather(
        val cityWeather: CityWeather
    ) : SearchScreenViewModelEvent()

    data class NavigateToWeatherScreen(
        val cityIndex: Int
    ) : SearchScreenViewModelEvent()

    data object NavigateUp : SearchScreenViewModelEvent()

    data class NavigateUpWithCityUpdateData(val cityName: String) : SearchScreenViewModelEvent()

}