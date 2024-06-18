package com.synexo.weatherapp.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.synexo.weatherapp.core.ui.Jaffa
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.core.ui.components.AcceptRejectDialog
import com.synexo.weatherapp.core.ui.components.ConfirmDialog
import com.synexo.weatherapp.core.ui.viewModel.rememberViewEvent
import com.synexo.weatherapp.settings.domain.model.ThemeType
import com.synexo.weatherapp.settings.presentation.components.SelectThemeDialog
import com.synexo.weatherapp.settings.presentation.components.SettingsOption
import com.synexo.weatherapp.settings.presentation.components.SettingsTopBar
import com.synexo.weatherapp.core.ui.R as CoreR

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    SettingsScreenContent(
        state = state,
        event = rememberViewEvent(viewModel)
    )
}

@Composable
fun SettingsScreenContent(
    state: SettingsScreenState,
    event: (SettingsScreenViewEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SettingsTopBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            if (state.resetSettingsDialogVisible) {
                AcceptRejectDialog(
                    title = CoreR.string.common_warning,
                    message = R.string.screen_settings_reset_settings_message,
                    acceptButtonText = CoreR.string.common_reset,
                    rejectButtonText = CoreR.string.common_cancel,
                    onAccept = { event(SettingsScreenViewEvent.ResetSettings) },
                    onReject = { event(SettingsScreenViewEvent.ResetSettingsDialogState(false)) },
                    onDismissRequest = {
                        event(
                            SettingsScreenViewEvent.ResetSettingsDialogState(
                                false
                            )
                        )
                    },
                )
            }

            if (state.clearAllLocationsDialogVisible) {
                AcceptRejectDialog(
                    title = CoreR.string.common_warning,
                    message = R.string.screen_settings_clear_locations_message,
                    acceptButtonText = CoreR.string.common_delete,
                    rejectButtonText = CoreR.string.common_cancel,
                    onAccept = { event(SettingsScreenViewEvent.ClearAllLocations) },
                    onReject = { event(SettingsScreenViewEvent.SetClearAllLocationsDialogState(false)) },
                    onDismissRequest = {
                        event(
                            SettingsScreenViewEvent.SetClearAllLocationsDialogState(
                                false
                            )
                        )
                    },
                )
            }

            if (state.aboutAppDialogVisible) {
                ConfirmDialog(
                    onDialogClose = { event(SettingsScreenViewEvent.SetAboutAppDialogState(false)) },
                    titleResId = R.string.screen_settings_dialog_about_app_title,
                    messageResId = R.string.screen_settings_dialog_about_app_message,
                    buttonTextResId = CoreR.string.common_ok
                )
            }

            if (state.selectThemeDialogVisible) {
                SelectThemeDialog(
                    selectedTheme = state.theme,
                    onDialogClose = {
                        event(SettingsScreenViewEvent.SetSelectThemeDialogState(false))
                        event(SettingsScreenViewEvent.OnThemeSelected(it))
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LocalCustomColorsPalette.current.contextBackground)
                    .padding(16.dp),
            ) {
                SettingsOption(
                    R.string.screen_settings_degrees,
                    "°C",
                    "°F",
                    state.degreesToggled,
                    onClick = { event(SettingsScreenViewEvent.OnDegreesClick) }
                )
                SettingsOption(
                    R.string.screen_settings_wind,
                    "m/s",
                    "km/h",
                    state.windToggled,
                    onClick = { event(SettingsScreenViewEvent.OnWindClick) }
                )
                SettingsOption(
                    R.string.screen_settings_pressure,
                    "hPa",
                    "mmHg",
                    state.pressureToggled,
                    onClick = { event(SettingsScreenViewEvent.OnPressureClick) }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LocalCustomColorsPalette.current.contextBackground)
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.screen_settings_theme),
                        color = LocalCustomColorsPalette.current.text
                    )
                    TextButton(
                        onClick = { event(SettingsScreenViewEvent.SetSelectThemeDialogState(true)) }
                    ) {
                        val id = when (state.theme) {
                            ThemeType.Light -> R.string.screen_settings_theme_light
                            ThemeType.Dark -> R.string.screen_settings_theme_dark
                            ThemeType.System -> R.string.screen_settings_theme_system
                        }
                        Text(
                            text = stringResource(id = id),
                            color = LocalCustomColorsPalette.current.textClickable
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(
                modifier = Modifier.align(Alignment.Start),
                onClick = { event(SettingsScreenViewEvent.SetAboutAppDialogState(true)) },
                content = {
                    Text(
                        stringResource(id = R.string.screen_settings_about_app),
                        color = LocalCustomColorsPalette.current.text
                    )
                }
            )

            TextButton(
                modifier = Modifier.align(Alignment.Start),
                onClick = { event(SettingsScreenViewEvent.SetClearAllLocationsDialogState(true)) },
                content = {
                    Text(
                        stringResource(id = R.string.screen_settings_clear_locations),
                        color = LocalCustomColorsPalette.current.text
                    )
                }
            )

            TextButton(
                modifier = Modifier.align(Alignment.Start),
                onClick = { event(SettingsScreenViewEvent.ResetSettingsDialogState(true)) },
                content = {
                    Text(
                        stringResource(id = R.string.screen_settings_reset_settings),
                        color = Jaffa
                    )
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun SettingsScreenContentPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            SettingsScreenContent(
                state = SettingsScreenState(),
                event = { },
            )
        }
    }
}