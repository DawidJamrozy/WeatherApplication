package com.synexo.weatherapp.settings.presentation

import android.os.Parcelable
import com.synexo.weatherapp.settings.domain.model.ThemeType
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingsScreenState(
    val selectThemeDialogVisible: Boolean = false,
    val clearAllLocationsDialogVisible: Boolean = false,
    val resetSettingsDialogVisible: Boolean = false,
    val aboutAppDialogVisible: Boolean = false,
    val degreesToggled: Boolean = false,
    val windToggled: Boolean = false,
    val pressureToggled: Boolean = false,
    val theme: ThemeType = ThemeType.System
) : Parcelable

sealed class SettingsScreenViewEvent {
    data class OnThemeSelected(val theme: ThemeType) : SettingsScreenViewEvent()
    data object OnDegreesClick : SettingsScreenViewEvent()
    data object OnWindClick : SettingsScreenViewEvent()
    data object OnPressureClick : SettingsScreenViewEvent()
    data class SetSelectThemeDialogState(val visible: Boolean) : SettingsScreenViewEvent()
    data class SetClearAllLocationsDialogState(val visible: Boolean) : SettingsScreenViewEvent()
    data class ResetSettingsDialogState(val visible: Boolean) : SettingsScreenViewEvent()
    data class SetAboutAppDialogState(val visible: Boolean) : SettingsScreenViewEvent()
    data object ClearAllLocations : SettingsScreenViewEvent()
    data object ResetSettings : SettingsScreenViewEvent()
}

sealed class SettingsScreenViewModelEvent