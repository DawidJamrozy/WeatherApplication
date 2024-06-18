package com.synexo.weatherapp.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.synexo.weatherapp.core.ui.LocalCustomColorsPalette
import com.synexo.weatherapp.core.ui.WeatherAppTheme

@Composable
fun CenteredProgress() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.background),
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center),
            color = LocalCustomColorsPalette.current.iconClickable
        )
    }
}

@PreviewLightDark
@Composable
fun CenteredProgressPreview() {
    WeatherAppTheme {
        Surface(
            color = LocalCustomColorsPalette.current.background
        ) {
            CenteredProgress()
        }
    }
}