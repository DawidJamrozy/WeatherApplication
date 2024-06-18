package com.synexo.weatherapp.futureDailyForecast.presentation

import androidx.lifecycle.SavedStateHandle
import com.synexo.weatherapp.core.ui.util.NavRoutes
import com.synexo.weatherapp.core.ui.viewModel.BaseStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FutureDailyForecastScreenViewModel @Inject constructor(
    state: SavedStateHandle
) : BaseStateViewModel<FutureDailyForecastScreenState, FutureDailyForecastScreenViewModelEvent, FutureDailyForecastScreenViewEvent>(
    state
) {

    init {
        val dailyIndex = state.get<Int>(NavRoutes.DAILY_INDEX_KEY)
        modify { copy(initialDayIndex = dailyIndex ?: 0)}
    }

    override fun defaultState(): FutureDailyForecastScreenState = FutureDailyForecastScreenState()

    override fun onViewEvent(event: FutureDailyForecastScreenViewEvent) {
        when (event) {
            is FutureDailyForecastScreenViewEvent.OnBackClick -> {
                setViewModelEvent(FutureDailyForecastScreenViewModelEvent.NavigateBack)
            }
            is FutureDailyForecastScreenViewEvent.SetCityWeather -> {
                modify { copy(cityWeather = event.cityWeather) }
            }
            is FutureDailyForecastScreenViewEvent.SetAbbreviatedDialogState -> {
                modify { copy(abbreviatedDialogVisible = event.visible) }
            }
        }
    }

}