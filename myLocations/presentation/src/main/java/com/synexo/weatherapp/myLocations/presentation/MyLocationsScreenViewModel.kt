package com.synexo.weatherapp.myLocations.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.synexo.weatherapp.core.domain.usecase.GetSavedLocationsWeatherDataUseCase
import com.synexo.weatherapp.core.model.Resource
import com.synexo.weatherapp.core.ui.viewModel.BaseStateViewModel
import com.synexo.weatherapp.myLocations.domain.usecase.DeleteCityUseCase
import com.synexo.weatherapp.myLocations.domain.usecase.UpdateCityOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyLocationsScreenViewModel @Inject constructor(
    private val getSavedLocationsWeatherDataUseCase: GetSavedLocationsWeatherDataUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
    private val updateCityOrderUseCase: UpdateCityOrderUseCase,
    state: SavedStateHandle,
) : BaseStateViewModel<MyLocationsScreenState, MyLocationsScreenViewModelEvent, MyLocationsScreenViewEvent>(
    state
) {

    override fun defaultState(): MyLocationsScreenState = MyLocationsScreenState()

    init {
        getSavedLocationsWeatherData()
    }

    override fun onViewEvent(event: MyLocationsScreenViewEvent) {
        when (event) {
            is MyLocationsScreenViewEvent.GoToWeatherScreen -> {
                setViewModelEvent(
                    MyLocationsScreenViewModelEvent.NavigateToWeatherScreen(event.cityIndex)
                )
            }

            is MyLocationsScreenViewEvent.GoToSearchScreen -> {
                viewModelScope.launch {
                    delay(150)
                    setViewModelEvent(MyLocationsScreenViewModelEvent.NavigateToSearchScreen)
                }
            }

            is MyLocationsScreenViewEvent.CityUpdated -> {
                modify {
                    copy(
                        cityUpdatedDialogData = CityUpdatedDialogData(event.cityName)
                    )
                }
            }

            is MyLocationsScreenViewEvent.HideCityUpdatedDialog -> {
                modify { copy(cityUpdatedDialogData = null) }
            }

            is MyLocationsScreenViewEvent.DismissErrorDialog -> {
                modify { copy(appError = null) }
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
                            modify {
                                copy(
                                    savedCities = it.data.weatherData,
                                    isLoading = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            modify {
                                copy(
                                    isLoading = false,
                                    appError = it.error
                                )
                            }
                        }

                        is Resource.Loading -> {
                            modify { copy(isLoading = true) }
                        }
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

    private fun deleteLocation(
        index: Int
    ) {
        modify { copy(deleteCityDialogData = null) }
        viewModelScope.launch {
            val cityWeather = getState().savedCities[index]
            deleteCityUseCase.invoke(DeleteCityUseCase.Params(cityWeather.id))
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