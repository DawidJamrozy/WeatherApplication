package com.synexo.weatherapp.settings.presentation.components
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.settings.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColorsPalette.current.background
        ),
        title = {
            Text(
                text = stringResource(R.string.screen_settings_title),
                style = MaterialTheme.typography.titleMedium,
                color = LocalCustomColorsPalette.current.text
            )
        },
    )
}

@PreviewLightDark
@Composable
fun SettingsTopBarPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            SettingsTopBar()
        }
    }
}

