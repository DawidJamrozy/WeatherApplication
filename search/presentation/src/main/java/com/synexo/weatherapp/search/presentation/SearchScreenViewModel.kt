package com.synexo.weatherapp.search.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.synexo.weatherapp.core.domain.usecase.GetSavedLocationsWeatherDataUseCase
import com.synexo.weatherapp.core.model.AppError
import com.synexo.weatherapp.core.model.Resource
import com.synexo.weatherapp.core.ui.viewModel.BaseStateViewModel
import com.synexo.weatherapp.search.domain.model.SearchResult
import com.synexo.weatherapp.search.domain.usecase.GetCurrentLocationWeatherUseCase
import com.synexo.weatherapp.search.domain.usecase.GetLocationWeatherFromAddressUseCase
import com.synexo.weatherapp.search.domain.usecase.SearchCityUseCase
import com.synexo.weatherapp.search.presentation.model.SearchCityDetails
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
class SearchScreenViewModel @Inject constructor(
    private val searchCityUseCase: SearchCityUseCase,
    private val getCurrentLocationWeatherUseCase: GetCurrentLocationWeatherUseCase,
    private val getLocationWeatherFromAddressUseCase: GetLocationWeatherFromAddressUseCase,
    private val getSavedLocationsWeatherDataUseCase: GetSavedLocationsWeatherDataUseCase,
    state: SavedStateHandle,
) : BaseStateViewModel<SearchScreenState, SearchScreenViewModelEvent, SearchScreenViewEvent>(
    state
) {

    override fun defaultState(): SearchScreenState = SearchScreenState()

    private val _searchInput = viewState.map { it.searchInput }
    private var getLocationJob: Job? = null

    init {
        registerSearchInputFlow()
        getSavedLocationsWeatherData()
    }

    override fun onViewEvent(event: SearchScreenViewEvent) {
        when (event) {
            is SearchScreenViewEvent.OnCityClicked -> {
                getLocationWeatherFromAddress(event.city)
            }

            is SearchScreenViewEvent.GoToWeatherScreen -> {
                setViewModelEvent(
                    SearchScreenViewModelEvent.NavigateToWeatherScreen(event.cityIndex)
                )
            }

            is SearchScreenViewEvent.DismissErrorDialog -> {
                modify { copy(appError = null) }
            }

            is SearchScreenViewEvent.SearchInputChanged -> {
                modify { copy(searchInput = event.input) }
            }

            is SearchScreenViewEvent.ClearSearchInput -> {
                getLocationJob?.cancel()
                modify { copy(searchInput = "", locations = listOf()) }
            }

            is SearchScreenViewEvent.CancelLocationSearch -> {
                getLocationJob?.cancel()
                setViewModelEvent(SearchScreenViewModelEvent.NavigateUp)
                modify {
                    copy(
                        isLocateMeInProgress = false
                    )
                }
            }

            is SearchScreenViewEvent.OnLocationPermissionGranted -> {
                getCurrentLocationWeatherData()
            }

            is SearchScreenViewEvent.AskForLocationPermission -> {
                modify { copy(askForLocationPermission = true) }
            }

            is SearchScreenViewEvent.SetLocationPermissionRationaleDialogState -> {
                modify { copy(showLocationPermissionRationaleDialog = event.isVisible) }
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
                            modify { copy(appError = it.error) }
                        }

                        else -> Unit
                    }
                }
        }
    }

    private fun getCurrentLocationWeatherData() {
        getLocationJob = viewModelScope.launch {
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
                            getLocationJob = null
                        }
                    }
                }
        }
    }

    private fun handleGetCurrentLocationSuccess(
        result: GetCurrentLocationWeatherUseCase.Result
    ) {
        getLocationJob = null
        val cityWeather = result.cityWeather
        when (result.searchResult) {
            SearchResult.NEW -> {
                setViewModelEvent(
                    SearchScreenViewModelEvent.NavigateToAddCityWeather(cityWeather)
                )
                modify { copy(isLocateMeInProgress = false) }
            }

            SearchResult.EXISTS -> {
                setViewModelEvent(
                    SearchScreenViewModelEvent.NavigateUpWithCityUpdateData(
                        cityWeather.cityDetails.name
                    )
                )
                modify { copy(isLocateMeInProgress = false) }
            }
        }
    }

    private fun getLocationWeatherFromAddress(city: SearchCityDetails) {
        val index = getState().locations.indexOf(city)
        getLocationJob = viewModelScope.launch {
            getLocationWeatherFromAddressUseCase
                .invoke(GetLocationWeatherFromAddressUseCase.Param(city.getFullName()))
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            changeButtonLoadingState(index, true)
                        }

                        is Resource.Success -> {
                            setViewModelEvent(
                                SearchScreenViewModelEvent.NavigateToAddCityWeather(
                                    it.data.weather
                                )
                            )
                            changeButtonLoadingState(index, false)
                        }

                        is Resource.Error -> {
                            modify { copy(appError = it.error) }
                            changeButtonLoadingState(index, false)
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

}