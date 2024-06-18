package com.synexo.weatherapp.addCity.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.synexo.weatherapp.addCity.domain.usecase.SaveWeatherCityDataUseCase
import com.synexo.weatherapp.core.ui.viewModel.BaseStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCityWeatherViewModel @Inject constructor(
    private val saveWeatherCityDataUseCase: SaveWeatherCityDataUseCase,
    state: SavedStateHandle
) : BaseStateViewModel<AddCityScreenState, AddCityScreenViewModelEvent, AddCityScreenViewEvent>(
    state
) {
    override fun defaultState(): AddCityScreenState = AddCityScreenState()

    override fun onViewEvent(event: AddCityScreenViewEvent) {
        when (event) {
            is AddCityScreenViewEvent.SetCityWeather -> {
                modify { copy(cityWeather = event.cityWeather) }
            }

            is AddCityScreenViewEvent.OnCancelClick -> {
                setViewModelEvent(AddCityScreenViewModelEvent.NavigateBack)
            }

            is AddCityScreenViewEvent.OnAddCityClick -> {
                saveNewCity()
            }

            is AddCityScreenViewEvent.OnDailyItemClick -> {
                setViewModelEvent(AddCityScreenViewModelEvent.GoToFutureDailyForecastScreen(event.index))
            }

            is AddCityScreenViewEvent.OnHourlyItemClick -> {
                setViewModelEvent(AddCityScreenViewModelEvent.GoToFutureHourlyForecastScreen)
            }
        }
    }

    private fun saveNewCity() {
        viewModelScope.launch {
            saveWeatherCityDataUseCase.invoke(SaveWeatherCityDataUseCase.Params(viewState.value.cityWeather!!))
            setViewModelEvent(AddCityScreenViewModelEvent.CityAddSuccess)
        }
    }
}