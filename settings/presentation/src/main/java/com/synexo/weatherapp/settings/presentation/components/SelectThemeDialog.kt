package com.synexo.weatherapp.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.settings.domain.model.ThemeType
import com.synexo.weatherapp.settings.presentation.R
import com.synexo.weatherapp.core.ui.R as CoreR

@Composable
fun SelectThemeDialog(
    selectedTheme: ThemeType,
    onDialogClose: (ThemeType) -> Unit
) {
    Dialog(
        onDismissRequest = { onDialogClose(selectedTheme) }
    ) {
        Card(
            modifier = Modifier
                .indication(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
                .background(Color.Transparent),
            content = {
                Column(
                    modifier = Modifier
                        .background(LocalCustomColorsPalette.current.background)
                        .padding(20.dp)
                ) {
                    ThemeRowItem(
                        isSelected = selectedTheme == ThemeType.Light,
                        theme = ThemeType.Light,
                        name = R.string.screen_settings_theme_light,
                        onClick = onDialogClose,
                        icon = CoreR.drawable.ic_sun
                    )
                    HorizontalDivider()
                    ThemeRowItem(
                        isSelected = selectedTheme == ThemeType.Dark,
                        theme = ThemeType.Dark,
                        name = R.string.screen_settings_theme_dark,
                        onClick = onDialogClose,
                        icon = CoreR.drawable.moon
                    )
                    HorizontalDivider()
                    ThemeRowItem(
                        isSelected = selectedTheme == ThemeType.System,
                        theme = ThemeType.System,
                        name = R.string.screen_settings_theme_system,
                        onClick = onDialogClose,
                        icon = R.drawable.ic_device,
                        iconColor = LocalCustomColorsPalette.current.iconClickable
                    )
                }
            }
        )
    }
}

@PreviewLightDark
@Composable
fun SelectThemeDialogPreview() {
    WeatherAppTheme {
        Surface {
            SelectThemeDialog(
                selectedTheme = ThemeType.Light,
                onDialogClose = {}
            )
        }
    }
}