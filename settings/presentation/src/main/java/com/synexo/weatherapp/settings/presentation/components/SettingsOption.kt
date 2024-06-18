package com.synexo.weatherapp.settings.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.settings.presentation.R

@Composable
fun SettingsOption(
    @StringRes title: Int,
    left: String,
    right: String,
    toggled: Boolean,
    onClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = title),
            color = LocalCustomColorsPalette.current.text
        )
        SwitchButton(
            leftText = left,
            rightText = right,
            onClick = onClick::invoke,
            isToogled = toggled,
            backgroundColor = LocalCustomColorsPalette.current.contextBackground,
            borderColor = LocalCustomColorsPalette.current.textClickable,
            highLightedColor = LocalCustomColorsPalette.current.textClickable,
        )
    }
}

@PreviewLightDark
@Composable
fun SettingsOptionPreview() {
    WeatherAppTheme {
        Surface {
            SettingsOption(
                title = R.string.screen_settings_theme,
                left = "Light",
                right = "Dark",
                toggled = false,
                onClick = {  }
            )
        }
    }
}