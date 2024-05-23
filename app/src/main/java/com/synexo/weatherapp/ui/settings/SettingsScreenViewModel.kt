package com.synexo.weatherapp.ui.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.synexo.weatherapp.data.model.ThemeType
import com.synexo.weatherapp.domain.model.DegreesUnit
import com.synexo.weatherapp.domain.model.PressureUnit
import com.synexo.weatherapp.domain.model.Theme
import com.synexo.weatherapp.domain.model.AppSettings
import com.synexo.weatherapp.domain.model.WindUnit
import com.synexo.weatherapp.domain.usecase.ClearAllLocationsUseCase
import com.synexo.weatherapp.domain.usecase.GetAppSettingsUseCase
import com.synexo.weatherapp.domain.usecase.SaveAppSettingsUseCase
import com.synexo.weatherapp.ui.base.BaseStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val saveAppSettingsUseCase: SaveAppSettingsUseCase,
    private val clearAllLocationsUseCase: ClearAllLocationsUseCase,
    state: SavedStateHandle
) : BaseStateViewModel<SettingsScreenState, SettingsScreenViewModelEvent, SettingsScreenViewEvent>(
    state
) {

    override fun defaultState(): SettingsScreenState = SettingsScreenState()

    init {
        getAppSettings()
    }

    override fun onViewEvent(event: SettingsScreenViewEvent) {
        when (event) {
            is SettingsScreenViewEvent.SetSelectThemeDialogState -> {
                modify { copy(selectThemeDialogVisible = event.visible) }
            }

            is SettingsScreenViewEvent.OnThemeSelected -> {
                modify { copy(theme = event.theme) }
                updateAppSettings()
            }

            is SettingsScreenViewEvent.OnDegreesClick -> {
                modify { copy(degreesToggled = !degreesToggled) }
                updateAppSettings()
            }

            is SettingsScreenViewEvent.OnWindClick -> {
                modify { copy(windToggled = !windToggled) }
                updateAppSettings()
            }

            is SettingsScreenViewEvent.OnPressureClick -> {
                modify { copy(pressureToggled = !pressureToggled) }
                updateAppSettings()
            }

            is SettingsScreenViewEvent.ClearAllLocations -> {
                modify { copy(clearAllLocationsDialogVisible = false) }
                clearAllLocations()
            }

            is SettingsScreenViewEvent.SetClearAllLocationsDialogState -> {
                modify { copy(clearAllLocationsDialogVisible = event.visible) }
            }

            is SettingsScreenViewEvent.ResetSettingsDialogState -> {
                modify { copy(resetSettingsDialogVisible = event.visible) }
            }

            is SettingsScreenViewEvent.ResetSettings -> {
                modify {
                    copy(
                        resetSettingsDialogVisible = false,
                        degreesToggled = false,
                        windToggled = false,
                        pressureToggled = false,
                        theme = ThemeType.System
                    )
                }
                updateAppSettings()
            }

            is SettingsScreenViewEvent.SetAboutAppDialogState -> {
                modify { copy(aboutAppDialogVisible = event.visible) }
            }
        }
    }

    private fun updateAppSettings() {
        viewModelScope.launch {
            val weatherSettings = mapStateToWeatherSettings(viewState.value)
            saveAppSettingsUseCase.invoke(SaveAppSettingsUseCase.Params(weatherSettings))

        }
    }

    private fun getAppSettings() {
        viewModelScope.launch {
            val settings = getAppSettingsUseCase
                .invoke(Unit)
                .first()
                .appSettings

            modify {
                copy(
                    degreesToggled = settings.degrees == DegreesUnit.Fahrenheit,
                    windToggled = settings.wind == WindUnit.KilometersPerHour,
                    pressureToggled = settings.pressure == PressureUnit.MillimetersOfMercury,
                    theme = when (settings.theme) {
                        Theme.Light -> ThemeType.Light
                        Theme.Dark -> ThemeType.Dark
                        Theme.System -> ThemeType.System
                    }

                )
            }
        }
    }

    private fun clearAllLocations() {
        viewModelScope.launch {
            clearAllLocationsUseCase.invoke(Unit)
        }
    }

    private fun mapStateToWeatherSettings(
        settingsScreenState: SettingsScreenState
    ): AppSettings {
        return AppSettings(
            degrees = when (settingsScreenState.degreesToggled) {
                true -> DegreesUnit.Fahrenheit
                false -> DegreesUnit.Celsius
            },
            wind = when (settingsScreenState.windToggled) {
                true -> WindUnit.KilometersPerHour
                false -> WindUnit.MetersPerSecond
            },
            pressure = when (settingsScreenState.pressureToggled) {
                true -> PressureUnit.MillimetersOfMercury
                false -> PressureUnit.Hectopascals
            },
            theme = when (settingsScreenState.theme) {
                ThemeType.Light -> Theme.Light
                ThemeType.Dark -> Theme.Dark
                ThemeType.System -> Theme.System
            }
        )
    }
}