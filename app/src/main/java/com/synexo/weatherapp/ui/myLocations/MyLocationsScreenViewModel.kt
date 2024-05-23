package com.synexo.weatherapp.ui.myLocations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.synexo.weatherapp.core.AppError
import com.synexo.weatherapp.core.Resource
import com.synexo.weatherapp.data.model.SearchCityDetails
import com.synexo.weatherapp.domain.model.SearchResult
import com.synexo.weatherapp.domain.usecase.DeleteLocationUseCase
import com.synexo.weatherapp.domain.usecase.GetCurrentLocationWeatherUseCase
import com.synexo.weatherapp.domain.usecase.GetLocationWeatherFromAddressUseCase
import com.synexo.weatherapp.domain.usecase.GetSavedLocationsWeatherDataUseCase
import com.synexo.weatherapp.domain.usecase.SearchCityUseCase
import com.synexo.weatherapp.domain.usecase.UpdateCityOrderUseCase
import com.synexo.weatherapp.ui.base.BaseStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MyLocationsScreenViewModel @Inject constructor(
    private val searchCityUseCase: SearchCityUseCase,
    private val getCurrentLocationWeatherUseCase: GetCurrentLocationWeatherUseCase,
    private val getLocationWeatherFromAddressUseCase: GetLocationWeatherFromAddressUseCase,
    private val getSavedLocationsWeatherDataUseCase: GetSavedLocationsWeatherDataUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase,
    private val updateCityOrderUseCase: UpdateCityOrderUseCase,
    state: SavedStateHandle,
) : BaseStateViewModel<MyLocationsScreenState, MyLocationsScreenViewModelEvent, MyLocationsScreenViewEvent>(
    state
) {

    override fun defaultState(): MyLocationsScreenState = MyLocationsScreenState()

    private val _searchInput = viewState.map { it.searchInput }
    private var getCurrentLocationJob: Job? = null

    init {
        registerSearchInputFlow()
        getSavedLocationsWeatherData()
    }

    override fun onViewEvent(event: MyLocationsScreenViewEvent) {
        when (event) {
            is MyLocationsScreenViewEvent.OnCityClicked -> {
                getLocationWeatherFromAddress(event.city)
            }

            is MyLocationsScreenViewEvent.GoToWeatherScreen -> {
                setViewModelEvent(
                    MyLocationsScreenViewModelEvent.NavigateToWeatherScreen(event.cityIndex)
                )
            }

            is MyLocationsScreenViewEvent.HideCityUpdatedDialog -> {
                modify { copy(cityUpdatedDialogData = null) }
            }

            is MyLocationsScreenViewEvent.DismissErrorDialog -> {
                modify { copy(appError = null) }
            }

            is MyLocationsScreenViewEvent.SearchInputChanged -> {
                modify { copy(searchInput = event.input) }
            }

            is MyLocationsScreenViewEvent.ClearSearchInput -> {
                modify { copy(searchInput = "", locations = listOf()) }
            }

            is MyLocationsScreenViewEvent.CancelLocationSearch -> {
                getCurrentLocationJob?.cancel()
                modify {
                    copy(
                        searchInput = "",
                        locations = listOf(),
                        isLocateMeInProgress = false
                    )
                }
            }

            is MyLocationsScreenViewEvent.OnLocationPermissionGranted -> {
                getCurrentLocationWeatherData()
            }

            is MyLocationsScreenViewEvent.AskForLocationPermission -> {
                modify { copy(askForLocationPermission = true) }
            }

            is MyLocationsScreenViewEvent.SetLocationPermissionRationaleDialogState -> {
                modify { copy(showLocationPermissionRationaleDialog = event.isVisible) }
            }

            is MyLocationsScreenViewEvent.ShowDeleteLocationDialog -> {
                modify {
                    copy(
                        deleteCityDialogData = DeleteCityDialogData(
                            index = event.index,
                            cityName = event.cityName
                        )
                    )
                }
            }

            is MyLocationsScreenViewEvent.HideDeleteLocationDialog -> {
                modify { copy(deleteCityDialogData = null) }
            }

            is MyLocationsScreenViewEvent.DeleteLocation -> {
                deleteLocation(event.index)
            }

            is MyLocationsScreenViewEvent.SwapCities -> {
                move(event.from, event.to)
            }

            is MyLocationsScreenViewEvent.SetSearchInputFocus -> {
                modify { copy(isSearchInputFocused = event.isFocused) }
            }

            is MyLocationsScreenViewEvent.SwitchEditMode -> {
                val isInEditMode = !getState().isInEditMode
                modify { copy(isInEditMode = isInEditMode) }
                if (isInEditMode) {
                    saveCurrentOrder()
                } else {
                    compareNewOrderAndUpdateIfNeeded()
                }
            }
        }
    }

    private fun getSavedLocationsWeatherData() {
        viewModelScope.launch {
            getSavedLocationsWeatherDataUseCase
                .invoke(Unit)
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            modify { copy(savedCities = it.data.weatherData) }
                        }

                        is Resource.Error -> {
                            it.error.printStackTrace()
                        }

                        else -> Unit
                    }
                }
        }
    }

    private fun saveCurrentOrder() {
        modify {
            copy(
                originalOrder = savedCities
                    .mapIndexed { index, cityWeather -> cityWeather.id to index }
                    .toMap()
            )
        }
    }

    private fun compareNewOrderAndUpdateIfNeeded() {
        val newOrder = getState()
            .savedCities
            .mapIndexed { index, cityWeather -> cityWeather.id to index }
            .toMap()

        if (newOrder != getState().originalOrder) {
            viewModelScope.launch {
                updateCityOrderUseCase.invoke(UpdateCityOrderUseCase.Params(newOrder))
            }
        }

        modify { copy(originalOrder = null) }
    }

    private fun getCurrentLocationWeatherData() {
        getCurrentLocationJob = viewModelScope.launch {
            getCurrentLocationWeatherUseCase
                .invoke(Unit)
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            modify {
                                copy(
                                    askForLocationPermission = false,
                                    isLocateMeInProgress = true
                                )
                            }
                        }

                        is Resource.Success -> {
                            handleGetCurrentLocationSuccess(it.data)
                        }

                        is Resource.Error -> {
                            modify {
                                copy(
                                    isLocateMeInProgress = false,
                                    appError = it.error
                                )
                            }
                            getCurrentLocationJob = null
                        }
                    }
                }
        }
    }

    private fun handleGetCurrentLocationSuccess(
        result: GetCurrentLocationWeatherUseCase.Result
    ) {
        getCurrentLocationJob = null
        val cityWeather = result.cityWeather
        when (result.searchResult) {
            SearchResult.NEW -> {
                setViewModelEvent(
                    MyLocationsScreenViewModelEvent.NavigateToAddCityWeather(cityWeather)
                )
                modify { copy(isLocateMeInProgress = false) }
            }

            SearchResult.EXISTS -> {
                val index =
                    getState().savedCities.indexOfFirst { city -> city.id == cityWeather.id }
                modify {
                    copy(
                        isLocateMeInProgress = false,
                        cityUpdatedDialogData = CityUpdatedDialogData(cityWeather.cityDetails.name),
                        isSearchInputFocused = false,
                        searchInput = "",
                        locations = listOf(),
                        savedCities = getState().savedCities.toMutableList().apply {
                            set(index, cityWeather)
                        }
                    )
                }
            }
        }
    }

    private fun getLocationWeatherFromAddress(city: SearchCityDetails) {
        val index = getState().locations.indexOf(city)
        viewModelScope.launch {
            getLocationWeatherFromAddressUseCase
                .invoke(GetLocationWeatherFromAddressUseCase.Param(city.getFullName()))
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            changeButtonLoadingState(index, true)
                        }

                        is Resource.Success -> {
                            setViewModelEvent(
                                MyLocationsScreenViewModelEvent.NavigateToAddCityWeather(
                                    it.data.weather
                                )
                            )
                            changeButtonLoadingState(index, false)
                        }

                        is Resource.Error -> {
                            modify { copy(appError = it.error) }
                            changeButtonLoadingState(index, false)
                            println(it.error.printStackTrace())
                        }
                    }
                }
        }
    }

    private fun changeButtonLoadingState(
        index: Int,
        isLoading: Boolean
    ) {
        val copyOfItem = getState().locations[index].copy(isLoading = isLoading)
        modify {
            copy(
                locations = getState().locations.toMutableList().apply { set(index, copyOfItem) },
                isLoading = isLoading
            )
        }
    }

    private fun deleteLocation(
        index: Int
    ) {
        modify { copy(deleteCityDialogData = null) }
        viewModelScope.launch {
            val cityWeather = getState().savedCities[index]
            deleteLocationUseCase.invoke(DeleteLocationUseCase.Params(cityWeather.id))
        }
    }

    @OptIn(FlowPreview::class)
    private fun registerSearchInputFlow() {
        viewModelScope.launch {
            _searchInput
                .distinctUntilChanged()
                .onEach { if (it.isBlank()) modify { copy(locations = listOf()) } }
                .filter { it.isNotBlank() }
                .debounce(1.seconds)
                .map { input ->
                    try {
                        val result = searchCityUseCase.invoke(SearchCityUseCase.Params(input))
                        result.cities.map {
                            SearchCityDetails(
                                primaryName = it.primaryText,
                                secondaryName = it.secondaryText,
                                placeId = it.placeId,
                                isLoading = false
                            )
                        }
                    } catch (e: AppError) {
                        modify { copy(appError = e) }
                        listOf()
                    } catch (e: Exception) {
                        modify { copy(appError = AppError.UnknownError(e.message)) }
                        listOf()
                    }
                }
                .collect { modify { copy(locations = it) } }
        }
    }

    private fun move(fromIndex: Int, toIndex: Int) {
        val newList = getState()
            .savedCities
            .toMutableList()
            .apply { add(toIndex, removeAt(fromIndex)) }

        modify { copy(savedCities = newList) }
    }

}