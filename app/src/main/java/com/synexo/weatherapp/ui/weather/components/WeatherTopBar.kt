package com.synexo.weatherapp.ui.weather.components


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.synexo.weatherapp.domain.model.weather.CityDetails
import com.synexo.weatherapp.ui.theme.LocalCustomColorsPalette
import com.synexo.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopBar(
    modifier: Modifier = Modifier,
    cityDetails: CityDetails,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColorsPalette.current.background
        ),
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = cityDetails.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = LocalCustomColorsPalette.current.text
                )
                Text(
                    text = "${cityDetails.administrativeArea}, ${cityDetails.country}",
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalCustomColorsPalette.current.textSecondary
                )
            }
        },
    )
}

@PreviewLightDark
@Composable
fun WeatherTopBarPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            WeatherTopBar(
                cityDetails = CityDetails(
                    name = "Rzeszów",
                    administrativeArea = "Podkarpackie",
                    country = "Poland",
                    language = "pl"
                )
            )
        }
    }
}