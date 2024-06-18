package com.synexo.weatherapp.weather.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme
import com.synexo.weatherapp.weather.presentation.R
import com.synexo.weatherapp.weather.presentation.WeatherScreenViewEvent

@Composable
fun NoSavedLocations(
    onEvent: (WeatherScreenViewEvent) -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = stringResource(R.string.screen_weather_no_weather_data_title),
                color = LocalCustomColorsPalette.current.text,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = stringResource(R.string.screen_weather_no_data_message),
                color = LocalCustomColorsPalette.current.textSecondary,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = { onEvent(WeatherScreenViewEvent.OnNoDataButtonClick) },
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalCustomColorsPalette.current.buttonBackground
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.screen_weather_no_data_button_text),
                    color = LocalCustomColorsPalette.current.buttonText,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun NoSavedLocationsPreview() {
    WeatherAppTheme {
        Surface(color = LocalCustomColorsPalette.current.background) {
            NoSavedLocations {
            }
        }
    }
}
