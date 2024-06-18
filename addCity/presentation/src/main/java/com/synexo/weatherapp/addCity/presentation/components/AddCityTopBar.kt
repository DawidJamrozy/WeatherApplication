package com.synexo.weatherapp.addCity.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.synexo.weatherapp.addCity.presentation.AddCityScreenViewEvent
import com.synexo.weatherapp.addCity.presentation.R
import com.synexo.weatherapp.core.model.weather.CityDetails
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.core.ui.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCityTopBar(
    modifier: Modifier = Modifier,
    cityDetails: CityDetails,
    onEvent: (AddCityScreenViewEvent) -> Unit,
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
        navigationIcon = {
            IconButton(
                onClick = { onEvent(AddCityScreenViewEvent.OnCancelClick) },
            ) {
                Icon(
                    tint = LocalCustomColorsPalette.current.iconClickable,
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = CoreR.string.common_cancel),
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onEvent(AddCityScreenViewEvent.OnAddCityClick) },
            ) {
                Icon(
                    tint = LocalCustomColorsPalette.current.iconClickable,
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.screen_add_city_add),
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun AddCityTopBarPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            AddCityTopBar(
                cityDetails = CityDetails(
                    name = "Rzeszów",
                    administrativeArea = "Podkarpackie",
                    country = "Poland",
                    language = "pl"
                ),
                onEvent = {}
            )
        }
    }
}