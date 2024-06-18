package com.synexo.weatherapp.core.ui.viewModel

import androidx.lifecycle.ViewModel
import com.synexo.weatherapp.core.model.CityWeather
import kotlinx.coroutines.flow.MutableStateFlow

class CityWeatherSharedViewModel : ViewModel() {

    private val _sharedState = MutableStateFlow<CityWeather?>(null)
    val sharedState = _sharedState

    fun updateState(cityWeather: CityWeather) {
        _sharedState.value = cityWeather
    }

}